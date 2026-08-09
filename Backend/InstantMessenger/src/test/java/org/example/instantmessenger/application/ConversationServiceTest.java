package org.example.instantmessenger.application;

import org.example.instantmessenger.application.dtos.MessageDto;
import org.example.instantmessenger.application.exceptions.ConversationNotFoundException;
import org.example.instantmessenger.application.exceptions.UserNotFoundException;
import org.example.instantmessenger.application.services.ConversationService;
import org.example.instantmessenger.domain.Conversation;
import org.example.instantmessenger.domain.User;
import org.example.instantmessenger.infrastructure.ConversationRepository;
import org.example.instantmessenger.infrastructure.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class ConversationServiceTest {
    @Mock
    private ConversationRepository conversationRepository;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private ConversationService conversationService;
    @Test
    void shoudCreateNewConversationIfDoesNotExist() {
        var receiverUUID = UUID.randomUUID();
        var senderUUID = UUID.randomUUID();
        var sender = mock(User.class);
        var receiver = mock(User.class);
        when(userRepository.findById(senderUUID)).thenReturn(Optional.of(sender));
        when(userRepository.findById(receiverUUID)).thenReturn(Optional.of(receiver));
        MessageDto.SendMessageEvent sendMessageEvent = new MessageDto.SendMessageEvent(null, senderUUID, receiverUUID, "siemka");
        conversationService.getOrCreatePrivateConversation(sendMessageEvent);
        verify(conversationRepository, times(1)).save(any(Conversation.class));
    }
    @Test
    void shoudNotCreateNewConversationIfAlreadyExists () {
        var receiverUUID = UUID.randomUUID();
        var senderUUID = UUID.randomUUID();
        var conversation = mock(Conversation.class);
        when(conversationRepository.findById(any())).thenReturn(Optional.ofNullable(conversation));
        MessageDto.SendMessageEvent sendMessageEvent = new MessageDto.SendMessageEvent(UUID.randomUUID(),senderUUID, receiverUUID, "siemka");
        conversationService.getOrCreatePrivateConversation(sendMessageEvent);
        verify(conversationRepository, never()).save(any(Conversation.class));
    }
    @Test
    void shoudNotCreateNewConversationIfUser1OrUser2DoesNotExist () {
        var receiverUUID = UUID.randomUUID();
        var senderUUID = UUID.randomUUID();
        MessageDto.SendMessageEvent sendMessageEvent = new MessageDto.SendMessageEvent(null, senderUUID, receiverUUID, "siemka");
        assertThrows(UserNotFoundException.class, () -> {
            conversationService.getOrCreatePrivateConversation(sendMessageEvent);
        });
        verify(conversationRepository, never()).save(any(Conversation.class));
    }
    @Test
    void shoudNotReturnConversationIfConversationDoesNotExist () {
        var conversationUUID = UUID.randomUUID();
        var senderUUID = UUID.randomUUID();
        MessageDto.SendMessageEvent sendMessageEvent = new MessageDto.SendMessageEvent(conversationUUID, senderUUID, null, "siemka");
        assertThrows(ConversationNotFoundException.class, () -> {
            conversationService.getOrCreatePrivateConversation(sendMessageEvent);
        });
    }
    @Test
    void shoudReturnConversationIfConversationExist () {
        var conversationUUID = UUID.randomUUID();
        var senderUUID = UUID.randomUUID();
        var conversationMock = mock(Conversation.class);
        when(conversationRepository.findById(any())).thenReturn(Optional.of(conversationMock));
        MessageDto.SendMessageEvent sendMessageEvent = new MessageDto.SendMessageEvent(conversationUUID, senderUUID, null, "siemka");
        var conversation =  conversationService.getOrCreatePrivateConversation(sendMessageEvent);
        assert (conversation.getClass() == Conversation.class);
    }
}
