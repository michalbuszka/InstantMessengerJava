package org.example.instantmessenger.application.exceptions;

import java.util.UUID;

public class ConversationMemberNotFoundException extends RuntimeException {
    public ConversationMemberNotFoundException(UUID uuid) {
        super("ConversationMember " + uuid + " not found.");
    }
}
