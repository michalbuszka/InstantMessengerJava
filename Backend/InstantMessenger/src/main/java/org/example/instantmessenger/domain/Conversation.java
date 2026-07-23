package org.example.instantmessenger.domain;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "conversations")
public class Conversation {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }
    private String name;
    private ConversationType type;

    public ConversationType getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
