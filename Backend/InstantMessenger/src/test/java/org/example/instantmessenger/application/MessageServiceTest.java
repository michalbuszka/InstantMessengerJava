package org.example.instantmessenger.application;

import org.example.instantmessenger.application.mappers.MessageMapper;
import org.example.instantmessenger.application.services.MessageService;
import org.example.instantmessenger.domain.Conversation;
import org.example.instantmessenger.domain.Message;
import org.example.instantmessenger.domain.User;
import org.example.instantmessenger.infrastructure.MessageRepository;
import org.example.instantmessenger.infrastructure.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MessageServiceTest {
    @Mock
    private MessageMapper messageMapper;
    @Mock
    private MessageRepository messageRepository;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private MessageService messageService;

    @Test
    void shouldSaveMessage () {
        when(userRepository.findById(any(UUID.class))).thenReturn(Optional.of(mock(User.class)));
        var conversation = mock(Conversation.class);
        when(messageMapper.map(any(), any(), any())).thenReturn(mock(Message.class));
        messageService.saveMessage(conversation, UUID.randomUUID(), "test content");
        verify(messageRepository, times(1)).save(any(Message.class));
    }
}
