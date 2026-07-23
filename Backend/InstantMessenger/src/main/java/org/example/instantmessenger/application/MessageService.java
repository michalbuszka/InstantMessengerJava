package org.example.instantmessenger.application;

import org.example.instantmessenger.application.dtos.MessageDtos;
import org.example.instantmessenger.application.exceptions.UserNotFoundException;
import org.example.instantmessenger.domain.Message;
import org.example.instantmessenger.domain.User;
import org.example.instantmessenger.infrastructure.MessageRepository;
import org.example.instantmessenger.infrastructure.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class MessageService {
    private MessageRepository messageRepository;
    private UserRepository userRepository;

    public MessageService(MessageRepository messageRepository, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
    }

    public void sendMessage(MessageDtos.SendMessage sendMessage, UUID senderId) {
        User sender = userRepository.findById(senderId).orElseThrow(() -> new UserNotFoundException(senderId));
        Message message = new Message();


    }
}
