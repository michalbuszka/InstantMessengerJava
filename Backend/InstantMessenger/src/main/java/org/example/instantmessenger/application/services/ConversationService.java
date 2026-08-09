package org.example.instantmessenger.application.services;

import org.example.instantmessenger.application.dtos.MessageDto;
import org.example.instantmessenger.application.exceptions.ConversationNotFoundException;
import org.example.instantmessenger.application.exceptions.UserNotFoundException;
import org.example.instantmessenger.domain.Conversation;
import org.example.instantmessenger.domain.ConversationType;
import org.example.instantmessenger.infrastructure.ConversationRepository;
import org.example.instantmessenger.infrastructure.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ConversationService {
    private final ConversationRepository conversationRepository;
    private final UserRepository userRepository;

    public ConversationService(ConversationRepository conversationRepository, UserRepository userRepository) {
        this.conversationRepository = conversationRepository;
        this.userRepository = userRepository;
    }

    public Conversation getOrCreatePrivateConversation(MessageDto.SendMessageEvent event) {
        if (event.conversationId() != null)
        {
            return conversationRepository.findById(event.conversationId()).orElseThrow(() -> new ConversationNotFoundException(event.conversationId()));
        }
        Optional<Conversation> c = conversationRepository.findConversationBetween(event.recevierId(), event.senderId(), ConversationType.direct);
        return c.orElseGet(() -> createPrivateConversation(event.senderId(), event.recevierId()));
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
