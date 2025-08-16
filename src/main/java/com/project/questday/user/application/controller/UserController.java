package com.project.questday.user.application.controller;

import com.project.questday.user.application.dto.controllerDto.UseUpdateProfileRequest;
import com.project.questday.user.application.dto.controllerDto.UserSaveRequest;
import com.project.questday.user.application.dto.controllerDto.UserUpdatePasswordRequest;
import com.project.questday.user.application.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequiredArgsConstructor
@RequestMapping("api/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public void userSave(@Valid @RequestBody UserSaveRequest userSaveRequest) {
        userService.userSave(userSaveRequest.userSaveDto());
    }

    @PatchMapping("/{email}/profile")
    public void updateProfile(@Valid @RequestBody UseUpdateProfileRequest useUpdateProfileRequest, @PathVariable String email) {
        userService.updateProfile(email,useUpdateProfileRequest.userUpdateProfileDto());
    }

    @PutMapping("/{email}/password")
    public void updatePassword(@Valid @RequestBody UserUpdatePasswordRequest userUpdatePasswordRequest, @PathVariable String email) {
        userService.updatePassword(email, userUpdatePasswordRequest.userUpdatePasswordDto());
    }

    @DeleteMapping("/{email}")
    public void userDelete(@PathVariable String email) {
        userService.userDelete(email);
    }


}
