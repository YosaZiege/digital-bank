package com.youssef_rahioui.bank.telegram;

import com.youssef_rahioui.bank.ai.RagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@Slf4j
public class BankTelegramBot extends TelegramLongPollingBot {

    private final RagService ragService;
    private final String botUsername;

    public BankTelegramBot(
            RagService ragService,
            @Value("${telegram.bot.token}") String botToken,
            @Value("${telegram.bot.username}") String botUsername) {
        super(botToken);
        this.ragService = ragService;
        this.botUsername = botUsername;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText()) return;

        String text = update.getMessage().getText();
        long chatId = update.getMessage().getChatId();

        if (text.equals("/start")) {
            send(chatId, "Welcome to BankBot! Ask me anything about your account, transfers, loans, or banking conditions.");
            return;
        }

        String answer = ragService.chat(text);
        send(chatId, answer);
    }

    private void send(long chatId, String text) {
        SendMessage message = SendMessage.builder()
                .chatId(chatId)
                .text(text)
                .build();
        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error("Failed to send Telegram message", e);
        }
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }
}
