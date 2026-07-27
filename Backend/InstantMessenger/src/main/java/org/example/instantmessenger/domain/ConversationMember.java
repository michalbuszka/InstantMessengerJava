package org.example.instantmessenger.domain;

import jakarta.persistence.*;
import org.example.instantmessenger.application.dtos.ConversationMemberDto;

import java.util.UUID;

@Entity
@Table(name="conversation_members")
public class ConversationMember {

    public ConversationMember(UUID id, Conversation conversation, User user, String userNick) {
        this.id = id;
        this.conversation = conversation;
        this.user = user;
        this.userNick = userNick;
    }
    public ConversationMember () {

    }

    public void setUserNick(String userNick) {
        this.userNick = userNick;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    public void setId(UUID id) {
        this.id = id;
    }

    @JoinColumn(name = "conversation_id")
    @ManyToOne

    private Conversation conversation;

    @JoinColumn(name = "user_id")
    @ManyToOne

    private User user;

    public User getUser() {
        return user;
    }

    public Conversation getConversation() {
        return conversation;
    }

    private String userNick;

    public String getUserNick() {
        return userNick;
    }

    public UUID getId() {
        return id;
    }

    public ConversationMemberDto.ConversationMember map () {
        return new ConversationMemberDto.ConversationMember(id, userNick);
    }

}
