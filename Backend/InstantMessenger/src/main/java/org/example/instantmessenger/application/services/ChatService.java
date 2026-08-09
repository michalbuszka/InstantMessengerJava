package org.example.instantmessenger.application.services;

import org.example.instantmessenger.application.dtos.ConvesationDto;
import org.example.instantmessenger.application.dtos.MessageDto;
import org.example.instantmessenger.application.exceptions.UserNotInConversationException;
import org.example.instantmessenger.domain.Conversation;
import org.example.instantmessenger.domain.ConversationType;
import org.example.instantmessenger.domain.User;
import org.example.instantmessenger.infrastructure.ConversationMemberRepository;
import org.example.instantmessenger.infrastructure.ConversationRepository;
import org.example.instantmessenger.infrastructure.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ChatService {
    private final MessageService messageService;
    private final ConversationService conversationService;
    private final ConversationMemberRepository conversationMemberRepository;
    private final ConversationMemberService conversationMemberService;
    private final ConversationRepository conversationRepository;
    private final UserRepository userRepository;

    @Autowired
    public ChatService(MessageService messageService, ConversationService conversationService, ConversationMemberRepository conversationMemberRepository, ConversationMemberService conversationMemberService, ConversationRepository conversationRepository, UserRepository userRepository) {
        this.messageService = messageService;
        this.conversationService = conversationService;
        this.conversationMemberRepository = conversationMemberRepository;
        this.conversationMemberService = conversationMemberService;
        this.conversationRepository = conversationRepository;
        this.userRepository = userRepository;
    }
    public void sendMessage(MessageDto.SendMessageEvent request, UUID senderId)
    {
        Conversation conversation = conversationService.getOrCreatePrivateConversation(request);
        messageService.saveMessage(conversation, senderId, request.messageContent());
    }
    public void editNick (MessageDto.EditNickRequest request, UUID senderId) {
        if (!conversationMemberRepository.existsByConversationIdAndUserId(senderId, request.conversationId()))
        {
            throw new UserNotInConversationException(senderId, request.conversationId());
        }
        conversationMemberService.changeNick(request.userId(), request.newNick());
    }
    public List<ConvesationDto.ContactsAndConvesationsDto> getConversationsAndContactsByQuery(String query, UUID user)
    {
        List<ConvesationDto.ContactsAndConvesationsDto> groupConversations = conversationRepository.findByNameAndUserId(query, user, ConversationType.group).stream().map(Conversation::map).toList();
        List<ConvesationDto.ContactsAndConvesationsDto> contacts = userRepository.findUserByNickContainingIgnoreCase(query).stream().map(User::map).toList();
        return Stream.concat(groupConversations.stream(), contacts.stream()).collect(Collectors.toList());
    }

}
