package org.example.instantmessenger.application.dtos;

import java.util.UUID;

public class MessageDtos {
    public record SendMessage(UUID conversationId, String messageContent) {}
}
