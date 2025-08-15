package com.project.questday.user.application.dto.serviceDto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserUpdateProfileInfo {

    private String userEmail;

    private String userNickname;

    public UserUpdateProfileInfo(String userEmail, String userNickname) {
        this.userEmail = userEmail;
        this.userNickname = userNickname;
    }
}
