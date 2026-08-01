package org.example.instantmessenger.domain;

import jakarta.persistence.*;
import org.example.instantmessenger.application.dtos.ConversationMemberDto;
import org.example.instantmessenger.application.dtos.ConvesationDto;

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

    public Conversation() {

    }

    public Conversation(String name, ConversationType type) {
        this.name = name;
        this.type = type;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }
    private String name;
    @Enumerated(EnumType.STRING)
    private ConversationType type;

    public ConversationType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    @ManyToMany
    @JoinTable(
            name = "conversation_members",
            joinColumns = @JoinColumn(name = "conversation_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> users;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public ConvesationDto.ContactsAndConvesationsDto map () {
        return new ConvesationDto.ContactsAndConvesationsDto(id, "conversation", name);
    }

}
