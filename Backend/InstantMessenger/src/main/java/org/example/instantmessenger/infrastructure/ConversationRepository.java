package org.example.instantmessenger.infrastructure;

import org.example.instantmessenger.domain.Conversation;
import org.example.instantmessenger.domain.ConversationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface ConversationRepository extends JpaRepository<Conversation, UUID> {
    @Query("""
                SELECT c FROM Conversation c
                JOIN c.users u1
                JOIN c.users u2
                WHERE u1.id = :user1Id
                  AND u2.id = :user2Id
                  AND c.type = :conversationType
            """)
    Optional<Conversation> findConversationBetween(
            @Param("userAId") UUID user1Id,
            @Param("userBId") UUID user2Id,
            @Param("conversationType") ConversationType conversationType
    );
}
