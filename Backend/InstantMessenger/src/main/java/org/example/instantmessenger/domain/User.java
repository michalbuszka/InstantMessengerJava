package org.example.instantmessenger.domain;

import jakarta.persistence.*;
import org.example.instantmessenger.application.dtos.ConvesationDto;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name="users")
public class User {
@Id
@GeneratedValue(strategy = GenerationType.UUID)
private UUID id;

    public User() {
    }

    public User(String name, String email, String password_hash) {
        this.nick = name;
        this.email = email;
        this.password_hash = password_hash;
    }

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

    public ConvesationDto.ContactsAndConvesationsDto map () {
        return new ConvesationDto.ContactsAndConvesationsDto(id, "contact", nick);
    }
}
