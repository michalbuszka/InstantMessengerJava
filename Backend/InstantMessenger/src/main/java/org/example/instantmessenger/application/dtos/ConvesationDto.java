package org.example.instantmessenger.application.dtos;

import org.example.instantmessenger.domain.ConversationType;

import java.util.UUID;

public class ConvesationDto {
    public record ContactsAndConvesationsDto(UUID Id, String type, String name) {}
}
