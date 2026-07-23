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

    public String getEmojiCode() {
        return emojiCode;
    }
}
