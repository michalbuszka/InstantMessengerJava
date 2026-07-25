package org.example.instantmessenger.domain;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name="reactions")
public class Reaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    private String emojiCode;

    @JoinColumn(name = "message_id")
    @ManyToOne
    private Message message;

    @JoinColumn(name = "user_id")
    @ManyToOne
    private User user;

    public String getEmojiCode() {
        return emojiCode;
    }

    public Message getMessage() {
        return message;
    }

    public User getUser() {
        return user;
    }
}
