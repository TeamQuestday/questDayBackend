package com.project.questday.user.application.dto.controllerDto;

import com.project.questday.user.application.dto.serviceDto.UserSaveInfo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;



@Getter
@NoArgsConstructor
public class UserSaveRequest {

    @Pattern(
            regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$",
            message = "유효한 이메일 형식이어야 합니다."
    )
    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    private String userEmail;

    @NotBlank(message = "닉네임은 필수 입력 값입니다.")
    private String userNickname;

    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&#])[A-Za-z\\d@$!%*?&#]{12,}$",
            message = "비밀번호는 특수문자(@$!%*?&#), 영어 소문자를 포함한 12자리 이상 입력하세요."
    )
    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    private String userPassword;

    @Builder
    public UserSaveRequest(String userEmail, String userNickname, String userPassword) {
        this.userEmail = userEmail;
        this.userNickname = userNickname;
        this.userPassword = userPassword;
    }

    public UserSaveInfo userSaveDto() {
        return UserSaveInfo.builder()
                .userEmail(this.userEmail)
                .userNickname(this.userNickname)
                .userPassword(this.userPassword)
                .build();
    }
}
