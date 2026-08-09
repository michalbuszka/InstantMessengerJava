package org.example.instantmessenger.api;

import org.example.instantmessenger.application.dtos.MessageDto;
import org.example.instantmessenger.application.services.ChatService;
import org.example.instantmessenger.infrastructure.messaging.RabbitConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/chat")
public class ChatController {
    private final RabbitTemplate rabbitTemplate;
    @Autowired
    public ChatController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    private ChatService chatService;
    @PostMapping("/message")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void sendMessage (@RequestBody MessageDto.SendMessageRequest sendMessageRequest) {
        MessageDto.SendMessageEvent sendMessageEvent = new MessageDto.SendMessageEvent(sendMessageRequest.conversationId(), null, sendMessageRequest.recevierId(), sendMessageRequest.messageContent());
        rabbitTemplate.convertAndSend(RabbitConfig.CHAT_EXCHANGE, RabbitConfig.CHAT_ROUTING_KEY, sendMessageEvent);
    }
}
