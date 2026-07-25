package org.example.instantmessenger.domain;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name="users")
public class User {
@Id
@GeneratedValue(strategy = GenerationType.UUID)
private UUID id;

    public void setId(UUID id) {
        this.id = id;
    }

    private String email;
    private String password_hash;
    private String nick;

    public String getEmail() {
        return email;
    }

    public String getPassword_hash() {
        return password_hash;
    }

    public String getNick() {
        return nick;
    }

    public UUID getId() {
        return id;
    }

    @OneToMany
    public List<Conversation> conversations;

    public List<Conversation> getConversations() {
        return conversations;
    }
}
