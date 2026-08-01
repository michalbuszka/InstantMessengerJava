package org.example.instantmessenger.infrastructure;

import org.example.instantmessenger.domain.Conversation;
import org.example.instantmessenger.domain.ConversationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
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
            @Param("user1Id") UUID user1Id,
            @Param("user2Id") UUID user2Id,
            @Param("conversationType") ConversationType conversationType
    );

    @Query("""
        SELECT c FROM Conversation c 
        JOIN c.users u 
        WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :query, '%')) 
          AND u.id = :userId 
          AND c.type = :conversationType
    """)
    List<Conversation> findByNameAndUserId(
            @Param("query") String query,
            @Param("userId") UUID userId,
            @Param("conversationType") ConversationType conversationType
    );
}
