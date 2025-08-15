package com.project.questday.user.application.dto.serviceDto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserUpdatePasswordInfo {

    private String userPassword;

    public UserUpdatePasswordInfo(String userPassword) {
        this.userPassword = userPassword;
    }
}
