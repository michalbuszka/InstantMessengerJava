package org.example.instantmessenger.application.dtos;

import java.util.UUID;

public class ConversationMemberDto {
    public record ConversationMember (UUID Id, String nick) {

    }
}
