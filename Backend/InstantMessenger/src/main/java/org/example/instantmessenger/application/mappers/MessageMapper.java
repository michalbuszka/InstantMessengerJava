package org.example.instantmessenger.application.mappers;

import org.example.instantmessenger.application.dtos.MessageDtos.SendMessageRequest;
import org.example.instantmessenger.domain.Conversation;
import org.example.instantmessenger.domain.Message;
import org.example.instantmessenger.domain.User;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class MessageMapper {
    public Message map (String messageContent, Conversation conversation, User sender) {
        return new Message(conversation, sender, messageContent, Instant.now());
    }
}
