package com.project.questday.user.application.dto.controllerDto;

import com.project.questday.user.application.dto.serviceDto.UserUpdateProfileInfo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UseUpdateProfileRequest {

    @Pattern(
            regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$",
            message = "유효한 이메일 형식이어야 합니다."
    )
    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    private String userEmail;

    @NotBlank(message = "닉네임은 필수 입력 값입니다.")
    private String userNickname;

    public UserUpdateProfileInfo userUpdateProfileDto() {
        return new UserUpdateProfileInfo(this.userEmail, this.userNickname);
    }

}
