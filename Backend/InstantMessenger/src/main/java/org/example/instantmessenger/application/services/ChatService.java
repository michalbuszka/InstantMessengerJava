package org.example.instantmessenger.application.services;

import org.example.instantmessenger.application.dtos.MessageDtos;
import org.example.instantmessenger.domain.Conversation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ChatService {
    private MessageService messageService;
    private ConversationService conversationService;
    @Autowired
    public ChatService(MessageService messageService, ConversationService conversationService) {
        this.messageService = messageService;
        this.conversationService = conversationService;
    }
    public void sendMessage(MessageDtos.SendMessageRequest request, UUID senderId)
    {
        Conversation conversation = conversationService.getOrCreateConversation(request, senderId);
    }
}
