package org.example.instantmessenger.application.services;

import org.example.instantmessenger.application.dtos.MessageDtos;
import org.example.instantmessenger.application.exceptions.UserNotFoundException;
import org.example.instantmessenger.application.mappers.MessageMapper;
import org.example.instantmessenger.domain.Conversation;
import org.example.instantmessenger.domain.Message;
import org.example.instantmessenger.domain.User;
import org.example.instantmessenger.infrastructure.MessageRepository;
import org.example.instantmessenger.infrastructure.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MessageService {
    private MessageMapper messageMapper;
    private MessageRepository messageRepository;
    private UserRepository userRepository;

    @Autowired
    public MessageService(MessageMapper messageMapper, MessageRepository messageRepository, UserRepository userRepository) {
        this.messageMapper = messageMapper;
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
    }

    public void saveMessage(Conversation conversation, UUID senderId, String messageContent) {
        User sender = userRepository.findById(senderId).orElseThrow(() -> new UserNotFoundException(senderId));
        Message message = messageMapper.map(messageContent, conversation, sender);
        messageRepository.save(message);
    }
}
