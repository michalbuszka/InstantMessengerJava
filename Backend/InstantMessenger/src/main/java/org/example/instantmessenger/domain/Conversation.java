package org.example.instantmessenger.domain;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "conversations")
public class Conversation {
    public Conversation(ConversationType type, List<User> users) {
        this.type = type;
        this.users = users;
    }

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

    @OneToMany
    private List<User> users;

    public List<User> getUsers() {
        return users;
    }
}
