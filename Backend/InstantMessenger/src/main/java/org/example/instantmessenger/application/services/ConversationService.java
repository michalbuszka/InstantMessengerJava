package org.example.instantmessenger.application.services;

import org.example.instantmessenger.application.dtos.MessageDtos;
import org.example.instantmessenger.application.exceptions.ConversationNotFoundException;
import org.example.instantmessenger.application.exceptions.UserNotFoundException;
import org.example.instantmessenger.domain.Conversation;
import org.example.instantmessenger.domain.ConversationType;
import org.example.instantmessenger.infrastructure.ConversationRepository;
import org.example.instantmessenger.infrastructure.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ConversationService {
    private ConversationRepository conversationRepository;
    private UserRepository userRepository;

    public ConversationService(ConversationRepository conversationRepository, UserRepository userRepository) {
        this.conversationRepository = conversationRepository;
        this.userRepository = userRepository;
    }

    public Conversation getOrCreateConversation (MessageDtos.SendMessageRequest request, UUID senderId) {
        if (request.conversationId() != null)
        {
            return conversationRepository.findById(request.conversationId()).orElseThrow(() -> new ConversationNotFoundException(request.conversationId()));
        }
        return createPrivateConversation(senderId, request.recevierId());
    }
    private Conversation createPrivateConversation(UUID user1, UUID user2)
    {
        var u1 = userRepository.findById(user1).orElseThrow(() -> new UserNotFoundException(user1));
        var u2 = userRepository.findById(user2).orElseThrow(() -> new UserNotFoundException(user2));
        var conversation = new Conversation(ConversationType.direct, List.of(u1, u2));
        conversationRepository.save(conversation);
        return conversation;
    }
}
