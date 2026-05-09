package com.youssef_rahioui.bank.ai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RagService {

    private final VectorStore vectorStore;
    private final ChatClient chatClient;

    public RagService(VectorStore vectorStore, ChatClient.Builder chatClientBuilder) {
        this.vectorStore = vectorStore;
        this.chatClient = chatClientBuilder.build();
    }

    public void ingest(List<String> textChunks) {
        List<Document> docs = textChunks.stream()
                .map(Document::new)
                .collect(Collectors.toList());
        vectorStore.add(docs);
    }

    public String chat(String question) {
        List<Document> relevant = vectorStore.similaritySearch(
                SearchRequest.builder().query(question).topK(5).build()
        );

        String context = relevant.stream()
                .map(Document::getText)
                .collect(Collectors.joining("\n\n"));

        String systemPrompt = context.isBlank()
                ? "You are a helpful banking assistant."
                : "You are a helpful banking assistant. Use the following context to answer:\n\n" + context;

        return chatClient.prompt()
                .system(systemPrompt)
                .user(question)
                .call()
                .content();
    }
}
