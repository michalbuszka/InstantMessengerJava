package org.example.instantmessenger.application.dtos;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public class MessageDtos {
    public record SendMessageRequest(UUID conversationId, UUID recevierId, @NotBlank String messageContent) {
    }
}
