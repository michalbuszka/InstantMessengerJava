package org.example.instantmessenger.application.services;

import org.example.instantmessenger.application.dtos.MessageDto;
import org.example.instantmessenger.domain.Conversation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ChatService {
    private final MessageService messageService;
    private final ConversationService conversationService;
    @Autowired
    public ChatService(MessageService messageService, ConversationService conversationService) {
        this.messageService = messageService;
        this.conversationService = conversationService;
    }
    public void sendMessage(MessageDto.SendMessageRequest request, UUID senderId)
    {
        Conversation conversation = conversationService.getOrCreatePrivateConversation(request, senderId);
        messageService.saveMessage(conversation, senderId, request.messageContent());
    }

}
