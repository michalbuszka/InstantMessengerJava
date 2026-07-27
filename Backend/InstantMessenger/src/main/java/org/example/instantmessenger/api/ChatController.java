package org.example.instantmessenger.api;

import org.example.instantmessenger.application.dtos.MessageDto;
import org.example.instantmessenger.application.services.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/chat")
public class ChatController {
    @Autowired
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    private ChatService chatService;
    @PostMapping("/message")
    @ResponseStatus(HttpStatus.OK)
    public void sendMessage (@RequestBody MessageDto.SendMessageRequest sendMessageRequest) {
        chatService.sendMessage(sendMessageRequest, UUID.randomUUID());
    }
}
