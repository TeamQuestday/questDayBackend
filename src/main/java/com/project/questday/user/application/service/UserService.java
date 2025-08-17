package com.project.questday.user.application.service;

import com.project.questday.global.exception.CustomApplicationException;
import com.project.questday.global.exception.ErrorCode;
import com.project.questday.user.application.dto.serviceDto.UserSaveInfo;
import com.project.questday.user.application.dto.serviceDto.UserUpdatePasswordInfo;
import com.project.questday.user.application.dto.serviceDto.UserUpdateProfileInfo;
import com.project.questday.user.domain.entity.User;
import com.project.questday.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public void userSave(UserSaveInfo userSaveInfo) {
        userRepository.save(User.builder()
                .userPassword(userSaveInfo.getUserPassword())
                .userNickname(userSaveInfo.getUserNickname())
                .userEmail(userSaveInfo.getUserEmail())
                .build());
    }

    @Transactional
    public void updateProfile(String email, UserUpdateProfileInfo userUpdateProfileInfo) {
        User user = userFindByEmail(email);
        user.updateProfile(userUpdateProfileInfo.getUserEmail(), userUpdateProfileInfo.getUserNickname());
    }

    @Transactional
    public void updatePassword(String email, UserUpdatePasswordInfo userUpdatePasswordInfo) {
        User user = userFindByEmail(email);
        user.updatePassword(userUpdatePasswordInfo.getUserPassword());
    }

    @Transactional
    public void userDelete(String email) {
        User user = userFindByEmail(email);
        userRepository.delete(user);
    }

    @Transactional(readOnly = true)
    public User userFindByEmail(String email) {
        return userRepository.findByUserEmail(email)
                .orElseThrow(() -> new CustomApplicationException(ErrorCode.EMAIL_NOT_FOUND));
    }


}
