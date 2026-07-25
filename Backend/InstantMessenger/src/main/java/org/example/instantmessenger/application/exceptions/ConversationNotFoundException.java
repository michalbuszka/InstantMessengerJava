package org.example.instantmessenger.application.exceptions;

import java.util.UUID;

public class ConversationNotFoundException extends RuntimeException {
    public ConversationNotFoundException(UUID uuid) {
        super("Conversation " + uuid + " not found.");
    }
}
