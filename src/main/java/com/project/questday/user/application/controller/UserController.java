package com.project.questday.user.application.controller;

import com.project.questday.user.application.dto.controllerDto.UseUpdateProfileRequest;
import com.project.questday.user.application.dto.controllerDto.UserSaveRequest;
import com.project.questday.user.application.dto.controllerDto.UserUpdatePasswordRequest;
import com.project.questday.user.application.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Void> userSave(@Valid @RequestBody UserSaveRequest userSaveRequest) {
        userService.userSave(userSaveRequest.userSaveDto());
        return ResponseEntity.status(HttpStatus.CREATED).build(); // 201 Created
    }

    @PatchMapping("/{email}/profile")
    public ResponseEntity<Void> updateProfile(@Valid @RequestBody UseUpdateProfileRequest request,
                                              @PathVariable String email) {
        userService.updateProfile(email, request.userUpdateProfileDto());
        return ResponseEntity.ok().build(); // 200 OK
    }

    @PutMapping("/{email}/password")
    public ResponseEntity<Void> updatePassword(@Valid @RequestBody UserUpdatePasswordRequest request,
                                               @PathVariable String email) {
        userService.updatePassword(email, request.userUpdatePasswordDto());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<Void> userDelete(@PathVariable String email) {
        userService.userDelete(email);
        return ResponseEntity.noContent().build(); // 204 No Content
    }


}
