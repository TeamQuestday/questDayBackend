package com.project.questday.user.domain.repository;

import com.project.questday.user.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUserEmail(String email);
    boolean existsByUserEmail(String email);
    Optional<User> findByUserNickname(String nickname);
}
