package org.example.instantmessenger.application.services;

import org.example.instantmessenger.application.dtos.ConversationMemberDto;
import org.example.instantmessenger.application.exceptions.ConversationMemberNotFoundException;
import org.example.instantmessenger.domain.ConversationMember;
import org.example.instantmessenger.infrastructure.ConversationMemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ConversationMemberService {
    private final ConversationMemberRepository conversationMemberRepository;

    public ConversationMemberService(ConversationMemberRepository conversationMemberRepository) {
        this.conversationMemberRepository = conversationMemberRepository;
    }
    public List<ConversationMemberDto.ConversationMember> getConversationMembers(UUID conversationId) {
        var members = conversationMemberRepository.getConversationMemberByConversationId(conversationId);
        return members.stream().map(ConversationMember::map).toList();
    }
    public ConversationMemberDto.ConversationMember changeNick (UUID conversationMemberId, String newNick) {
        var conversationMember = conversationMemberRepository.findById(conversationMemberId).orElseThrow(() -> new ConversationMemberNotFoundException(conversationMemberId));
        conversationMember.setUserNick(newNick);
        conversationMemberRepository.save(conversationMember);
        return conversationMember.map();
    }
}
