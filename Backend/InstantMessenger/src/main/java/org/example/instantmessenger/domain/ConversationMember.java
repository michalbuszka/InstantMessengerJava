package org.example.instantmessenger.domain;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name="conversation_members")
public class ConversationMember {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    public void setId(UUID id) {
        this.id = id;
    }

    private String userNick;

    public String getUserNick() {
        return userNick;
    }

    public UUID getId() {
        return id;
    }
}
