package org.example.instantmessenger.application.dtos;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public class MessageDto {
    public record SendMessageRequest(UUID conversationId, UUID recevierId, @NotBlank String messageContent) {
    }
    public record SendMessageEvent(UUID conversationId, UUID senderId, UUID recevierId, @NotBlank String messageContent) {
    }
    public record EditNickRequest(@NotBlank UUID userId, @NotBlank UUID conversationId, @NotBlank String newNick) {}
}
