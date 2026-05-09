package com.youssef_rahioui.bank.controllers;

import com.youssef_rahioui.bank.ai.RagService;
import com.youssef_rahioui.bank.dtos.ChatRequestDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/ai")
@AllArgsConstructor
public class AiChatController {

    private final RagService ragService;

    @PostMapping("/chat")
    public ResponseEntity<Map<String, String>> chat(@RequestBody ChatRequestDTO request) {
        String answer = ragService.chat(request.getQuestion());
        return ResponseEntity.ok(Map.of("answer", answer));
    }
}
