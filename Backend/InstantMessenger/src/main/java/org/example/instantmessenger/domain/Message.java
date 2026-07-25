package org.example.instantmessenger.domain;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @JoinColumn(name = "conversation_id")

    @ManyToOne
    private Conversation conversation;

    public Message(Conversation conversation, User sender, String content, Instant send_date) {
        this.conversation = conversation;
        this.sender = sender;
        this.content = content;
        this.send_date = send_date;
    }

    @JoinColumn(name="sender_id")
    @ManyToOne
    private User sender;

    private String content;
    private Instant send_date;

    @OneToMany
    List<Reaction> reactions;

    public Message() {

    }

    public String getContent() {
        return content;
    }

    public Instant getSend_date() {
        return send_date;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public Conversation getConversation() {
        return conversation;
    }

    public User getSender() {
        return sender;
    }

    public List<Reaction> getReactions() {
        return reactions;
    }
}
