package org.example.instantmessenger.infrastructure;

import org.example.instantmessenger.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    List<User> findUserByNickContainingIgnoreCase(String nick);
}
