package com.project.questday.user.application.dto.controllerDto;

import com.project.questday.user.application.dto.serviceDto.UserUpdatePasswordInfo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserUpdatePasswordRequest {

    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&#])[A-Za-z\\d@$!%*?&#]{12,}$",
            message = "비밀번호는 특수문자(@$!%*?&#), 영어 소문자를 포함한 12자리 이상 입력하세요."
    )
    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    private String userPassword;

    public UserUpdatePasswordInfo userUpdatePasswordDto() {
        return new UserUpdatePasswordInfo(this.userPassword);
    }

}
