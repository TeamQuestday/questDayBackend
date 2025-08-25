package com.project.questday.user.controller;

import com.project.questday.user.domain.entity.User;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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

    // --- CREATE (회원가입) ---
    @Test
    void postUser_shouldReturnCreated_whenValidRequest() throws Exception {
        String json = """
            {
              "userEmail": "test@example.com",
              "userNickname": "nick",
              "userPassword": "pass1234!@#$abcd"
            }
            """;

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated()); // 201 Created
    }

    @Test
    void postUser_shouldReturnBadRequest_whenInvalidEmail() throws Exception {
        String json = """
            {
              "userEmail": "invalidEmail",
              "userNickname": "nick",
              "userPassword": "pass1234!@#$abcd"
            }
            """;

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest()); // @Valid 실패
    }

    @Test
    void postUser_shouldReturnConflict_whenDuplicateEmail() throws Exception {
        // given
        userRepository.save(User.builder()
                .userEmail("dup@example.com")
                .userNickname("nick")
                .userPassword("validpass1234!@#")
                .build());

        String json = """
            {
              "userEmail": "dup@example.com",
              "userNickname": "nick2",
              "userPassword": "validpass1234!@#"
            }
            """;

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isConflict()); // 중복 이메일 처리
    }

    // --- UPDATE PROFILE ---
    @Test
    void patchProfile_shouldReturnOk_whenValidRequest() throws Exception {
        userRepository.save(User.builder()
                .userEmail("profile@example.com")
                .userNickname("beforeNick")
                .userPassword("validpass1234!@#")
                .build());

        String json = """
            {
              "userEmail": "profile@example.com",
              "userNickname": "afterNick"
            }
            """;

        mockMvc.perform(patch("/api/users/profile@example.com/profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk()); // 200 OK
    }

    @Test
    void patchProfile_shouldReturnBadRequest_whenInvalidEmailFormat() throws Exception {
        String json = """
            {
              "userEmail": "invalidEmail",
              "userNickname": "nick"
            }
            """;

        mockMvc.perform(patch("/api/users/invalidEmail/profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest()); // 400 Bad Request
    }

    @Test
    void patchProfile_shouldReturnNotFound_whenUserNotExist() throws Exception {
        String json = """
            {
              "userEmail": "notfound@example.com",
              "userNickname": "nick"
            }
            """;

        mockMvc.perform(patch("/api/users/notfound@example.com/profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNotFound()); // 404 Not Found
    }

    // --- UPDATE PASSWORD ---
    @Test
    void putPassword_shouldReturnOk_whenValidPassword() throws Exception {
        userRepository.save(User.builder()
                .userEmail("pw@example.com")
                .userNickname("nick")
                .userPassword("oldPass1234!@#")
                .build());

        String json = """
            {
              "userPassword": "newPass1234!@#"
            }
            """;

        mockMvc.perform(put("/api/users/pw@example.com/password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk()); // 200 OK
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
                .andExpect(status().isBadRequest()); // 400 Bad Request
    }

    @Test
    void putPassword_shouldReturnNotFound_whenUserNotExist() throws Exception {
        String json = """
            {
              "userPassword": "validPass1234!@#"
            }
            """;

        mockMvc.perform(put("/api/users/notfound@example.com/password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNotFound()); // 404 Not Found
    }

    // --- DELETE USER ---
    @Test
    void deleteUser_shouldReturnNoContent_whenValidEmail() throws Exception {
        userRepository.save(User.builder()
                .userEmail("delete@example.com")
                .userNickname("deleteNick")
                .userPassword("validpass1234!@#")
                .build());

        mockMvc.perform(delete("/api/users/delete@example.com"))
                .andExpect(status().isNoContent()); // 204 No Content
    }

    @Test
    void deleteUser_shouldReturnNotFound_whenEmailNotExist() throws Exception {
        mockMvc.perform(delete("/api/users/notfound@example.com"))
                .andExpect(status().isNotFound()); // 404 Not Found
    }
}

