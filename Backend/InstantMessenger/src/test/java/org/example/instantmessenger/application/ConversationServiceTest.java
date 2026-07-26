package org.example.instantmessenger.application;

import org.example.instantmessenger.application.dtos.MessageDtos;
import org.example.instantmessenger.application.exceptions.ConversationNotFoundException;
import org.example.instantmessenger.application.exceptions.UserNotFoundException;
import org.example.instantmessenger.application.services.ConversationService;
import org.example.instantmessenger.domain.Conversation;
import org.example.instantmessenger.domain.Message;
import org.example.instantmessenger.domain.User;
import org.example.instantmessenger.infrastructure.ConversationRepository;
import org.example.instantmessenger.infrastructure.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.swing.text.html.Option;
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
        MessageDtos.SendMessageRequest sendMessageRequest = new MessageDtos.SendMessageRequest(null, receiverUUID, "siemka");
        conversationService.getOrCreatePrivateConversation(sendMessageRequest, senderUUID);
        verify(conversationRepository, times(1)).save(any(Conversation.class));
    }
    @Test
    void shoudNotCreateNewConversationIfAlreadyExists () {
        var receiverUUID = UUID.randomUUID();
        var senderUUID = UUID.randomUUID();
        var conversation = mock(Conversation.class);
        when(conversationRepository.findById(any())).thenReturn(Optional.ofNullable(conversation));
        MessageDtos.SendMessageRequest sendMessageRequest = new MessageDtos.SendMessageRequest(UUID.randomUUID(), receiverUUID, "siemka");
        conversationService.getOrCreatePrivateConversation(sendMessageRequest, senderUUID);
        verify(conversationRepository, never()).save(any(Conversation.class));
    }
    @Test
    void shoudNotCreateNewConversationIfUser1OrUser2DoesNotExist () {
        var receiverUUID = UUID.randomUUID();
        var senderUUID = UUID.randomUUID();
        MessageDtos.SendMessageRequest sendMessageRequest = new MessageDtos.SendMessageRequest(null, receiverUUID, "siemka");
        assertThrows(UserNotFoundException.class, () -> {
            conversationService.getOrCreatePrivateConversation(sendMessageRequest, senderUUID);
        });
        verify(conversationRepository, never()).save(any(Conversation.class));
    }
    @Test
    void shoudNotReturnConversationIfConversationDoesNotExist () {
        var conversationUUID = UUID.randomUUID();
        var senderUUID = UUID.randomUUID();
        MessageDtos.SendMessageRequest sendMessageRequest = new MessageDtos.SendMessageRequest(conversationUUID, null, "siemka");
        assertThrows(ConversationNotFoundException.class, () -> {
            conversationService.getOrCreatePrivateConversation(sendMessageRequest, senderUUID);
        });
    }
    @Test
    void shoudReturnConversationIfConversationExist () {
        var conversationUUID = UUID.randomUUID();
        var senderUUID = UUID.randomUUID();
        var conversationMock = mock(Conversation.class);
        when(conversationRepository.findById(any())).thenReturn(Optional.of(conversationMock));
        MessageDtos.SendMessageRequest sendMessageRequest = new MessageDtos.SendMessageRequest(conversationUUID, null, "siemka");
        var conversation =  conversationService.getOrCreatePrivateConversation(sendMessageRequest, senderUUID);
        assert (conversation.getClass() == Conversation.class);
    }
}
