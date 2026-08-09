package org.example.instantmessenger.infrastructure.messaging;

import org.example.instantmessenger.application.dtos.MessageDto;
import org.example.instantmessenger.application.services.ChatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ChatMessageConsumer {

    private final ChatService chatService;
    private static final Logger logger = LoggerFactory.getLogger(ChatMessageConsumer.class);

    public ChatMessageConsumer(ChatService chatService) {
        this.chatService = chatService;
    }

    @RabbitListener(queues = RabbitConfig.CHAT_QUEUE)
    public void handleChatMessage(MessageDto.SendMessageEvent request) {
        logger.info("Consuming: " + request.messageContent());
        chatService.sendMessage(request, UUID.randomUUID());
    }
}
