package com.project.questday.user.repository;

import com.project.questday.user.domain.entity.User;
import com.project.questday.user.domain.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void findByUserEmail_shouldReturnUser_whenExists() {
        User user = new User("test@example.com", "nick", "password");
        userRepository.save(user);

        Optional<User> found = userRepository.findByUserEmail("test@example.com");
        assertThat(found).isPresent();
        assertThat(found.get().getUserEmail()).isEqualTo("test@example.com");
    }

    @Test
    void findByUserEmail_shouldReturnEmpty_whenNotExists() {
        Optional<User> found = userRepository.findByUserEmail("missing@example.com");
        assertThat(found).isEmpty();
    }

    @Test
    void delete_shouldApplyLogicalDeletion() {
        User user = new User("del@example.com", "nick", "password");
        userRepository.save(user);

        userRepository.delete(user);

        Optional<User> found = userRepository.findByUserEmail("del@example.com");
        assertThat(found).isEmpty(); // 논리 삭제 적용
    }
}

