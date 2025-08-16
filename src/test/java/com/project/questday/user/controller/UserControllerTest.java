package com.project.questday.user.controller;

import com.project.questday.user.domain.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    void postUser_shouldReturnOk_whenValidRequest() throws Exception {
        String json = """
            {
              "userEmail": "test@example.com",
              "userNickname": "nick",
              "userPassword": "Pass1234!@#$"
            }
            """;

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }

    @Test
    void patchProfile_shouldReturnBadRequest_whenInvalidEmail() throws Exception {
        String json = """
            {
              "userEmail": "invalidEmail",
              "userNickname": "nick"
            }
            """;

        mockMvc.perform(patch("/api/users/test@example.com/profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    void putPassword_shouldReturnBadRequest_whenPolicyNotMet() throws Exception {
        String json = """
            {
              "userPassword": "short"
            }
            """;

        mockMvc.perform(put("/api/users/test@example.com/password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());
    }
}

