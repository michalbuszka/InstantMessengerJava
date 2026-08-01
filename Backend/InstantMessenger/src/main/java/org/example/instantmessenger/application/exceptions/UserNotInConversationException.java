package org.example.instantmessenger.application.exceptions;

import java.util.UUID;

public class UserNotInConversationException extends RuntimeException {
    public UserNotInConversationException(UUID user, UUID conversation) {
        super("User " + user + " not found in conversation " + conversation + ".");
    }
}
