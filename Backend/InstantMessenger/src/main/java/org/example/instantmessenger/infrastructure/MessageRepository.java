package org.example.instantmessenger.infrastructure;

import org.example.instantmessenger.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MessageRepository extends JpaRepository<Message, UUID> {
}
