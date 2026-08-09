package org.example.instantmessenger.application;

import org.example.instantmessenger.application.dtos.ConvesationDto;
import org.example.instantmessenger.application.dtos.MessageDto;
import org.example.instantmessenger.application.services.ChatService;
import org.example.instantmessenger.application.services.ConversationService;
import org.example.instantmessenger.domain.Conversation;
import org.example.instantmessenger.domain.ConversationMember;
import org.example.instantmessenger.domain.ConversationType;
import org.example.instantmessenger.domain.User;
import org.example.instantmessenger.infrastructure.ConversationMemberRepository;
import org.example.instantmessenger.infrastructure.ConversationRepository;
import org.example.instantmessenger.infrastructure.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ConversationIT {

    @Autowired
    private ConversationRepository conversationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConversationMemberRepository conversationMemberRepository;

    @Autowired
    private ChatService chatService;
    @Autowired
    private ConversationService conversationService;

    @Test
    @DisplayName("Powinien zwrócić połączoną listę grup i kontaktów pasujących do frazy")
    void shouldReturnCombinedGroupConversationsAndContacts() {
        String searchQuery = "Jan";

        User currentUser = userRepository.save(
                new User("MojeKonto", "user_" + UUID.randomUUID() + "@example.com", "hashed_password")
        );
        User matchingContact = userRepository.save(
                new User("Janusz", "janusz_" + UUID.randomUUID() + "@example.com", "hashed_password")
        );
        User nonMatchingContact = userRepository.save(
                new User("Adam", "adam_" + UUID.randomUUID() + "@example.com", "hashed_password")
        );

        Conversation groupConversation = conversationRepository.save(
                new Conversation("Grupa Janek", ConversationType.group)
        );

        conversationMemberRepository.save(
                new ConversationMember(null, groupConversation, currentUser, currentUser.getNick())
        );

        List<ConvesationDto.ContactsAndConvesationsDto> result =
                chatService.getConversationsAndContactsByQuery(searchQuery, currentUser.getId());

        assertThat(result).hasSize(2);
        assertThat(result)
                .extracting("name")
                .containsExactlyInAnyOrder("Grupa Janek", "Janusz");
    }
    @Test
    @DisplayName("Should create new conversation when sending message.")
    void shouldCreateNewConversationWhenSendingMessage ()
    {
        User user1 = userRepository.save(
                new User("Janusz", "janusz_" + UUID.randomUUID() + "@example.com", "hashed_password")
        );
        User user2 = userRepository.save(
                new User("Adam", "adam_" + UUID.randomUUID() + "@example.com", "hashed_password")
        );
        MessageDto.SendMessageEvent sendMessageEvent = new MessageDto.SendMessageEvent(null, user1.getId(), user2.getId(), "siemka");
        var c = conversationService.getOrCreatePrivateConversation(sendMessageEvent);
        assertThat(c).isNotNull();
    }
}