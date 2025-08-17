package com.project.questday.user.service;


import com.project.questday.global.exception.CustomApplicationException;
import com.project.questday.global.exception.ErrorCode;
import com.project.questday.user.application.dto.serviceDto.UserUpdatePasswordInfo;
import com.project.questday.user.application.dto.serviceDto.UserUpdateProfileInfo;
import com.project.questday.user.application.service.UserService;
import com.project.questday.user.domain.entity.User;
import com.project.questday.user.domain.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void updateProfile_shouldUpdate_whenUserExists() {
        User user = new User("old@example.com", "oldNick", "password");
        UserUpdateProfileInfo dto = new UserUpdateProfileInfo("new@example.com", "newNick");
        when(userRepository.findByUserEmail("old@example.com")).thenReturn(Optional.of(user));

        userService.updateProfile("old@example.com", dto);

        assertThat(user.getUserEmail()).isEqualTo("new@example.com");
        assertThat(user.getUserNickname()).isEqualTo("newNick");
    }

    @Test
    void updateProfile_shouldThrow_whenUserNotFound() {
        when(userRepository.findByUserEmail("missing@example.com")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.updateProfile("missing@example.com",
                new UserUpdateProfileInfo("email", "nick")))
                .isInstanceOf(CustomApplicationException.class)
                .hasMessage(ErrorCode.EMAIL_NOT_FOUND.getMessage());
    }

    @Test
    void updatePassword_shouldUpdate_whenUserExists() {
        User user = new User("user@example.com", "nick", "oldPass");
        UserUpdatePasswordInfo dto = new UserUpdatePasswordInfo("newPass123!@#");
        when(userRepository.findByUserEmail("user@example.com")).thenReturn(Optional.of(user));

        userService.updatePassword("user@example.com", dto);

        assertThat(user.getUserPassword()).isEqualTo("newPass123!@#");
    }

    @Test
    void userDelete_shouldCallDelete_whenUserExists() {
        User user = new User("del@example.com", "nick", "pass");
        when(userRepository.findByUserEmail("del@example.com")).thenReturn(Optional.of(user));

        userService.userDelete("del@example.com");

        verify(userRepository).delete(user);
    }
}

