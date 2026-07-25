package org.example.instantmessenger.api;

import org.example.instantmessenger.application.dtos.MessageDtos;
import org.example.instantmessenger.application.services.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class ChatController {
    @Autowired
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    private ChatService chatService;
    @PostMapping("/message")
    public void sendMessage (@RequestBody MessageDtos.SendMessageRequest sendMessageRequest) {
        chatService.sendMessage(sendMessageRequest, UUID.randomUUID());
    }
}
