package com.project.questday.user.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.Set;

import com.project.questday.user.application.dto.controllerDto.UserSaveRequest;

import static org.assertj.core.api.Assertions.assertThat;

class UserSaveRequestTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void shouldFailValidation_whenEmailInvalid() {
        UserSaveRequest request = UserSaveRequest.builder()
                .userEmail("invalid")
                .userNickname("nick")
                .userPassword("Password123!@#")
                .build();

        Set<ConstraintViolation<UserSaveRequest>> violations = validator.validate(request);
        assertThat(violations).isNotEmpty();
    }

    @Test
    void shouldPassValidation_whenAllValid() {
        UserSaveRequest request = UserSaveRequest.builder()
                .userEmail("valid@example.com")
                .userNickname("nick")
                .userPassword("Password123!@#")
                .build();

        Set<ConstraintViolation<UserSaveRequest>> violations = validator.validate(request);
        assertThat(violations).isEmpty();
    }
}

