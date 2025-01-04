package com.ed.authservice.auth.adapter.in.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ed.authservice.auth.adapter.in.web.dto.SignUpRequest;
import com.ed.authservice.auth.application.port.in.AuthSingUpCommand;
import com.ed.authservice.auth.application.port.out.AuthSignUpResponse;
import com.ed.authservice.auth.application.service.AuthService;
import com.ed.authservice.auth.domain.UserRole;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(AuthController.class)
@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockitoBean
  private AuthService authService;

  @Nested
  @DisplayName("SignUpTest")
  class SignUp {

    @Test
    @DisplayName("Should sign up success")
    void shouldSignUpSuccess() throws Exception {
      //given
      final String uri = "/api/v1/auth/sign-up";

      final SignUpRequest signUpRequest = SignUpRequest.builder()
          .username("test")
          .password("Test@123")
          .build();

      final AuthSignUpResponse authSignUpResponse = AuthSignUpResponse.builder()
          .username("test")
          .publicId(UUID.randomUUID().toString())
          .userRole(UserRole.DEFAULT_CUSTOMER)
          .build();

      given(authService.signUp(any(AuthSingUpCommand.class))).willReturn(authSignUpResponse);

      //when
      ResultActions resultActions = mockMvc.perform(post(uri)
          .contentType(MediaType.APPLICATION_JSON)
          .content(objectMapper.writeValueAsString(signUpRequest)));

      //then
      resultActions.andExpect(status().isCreated())
          .andExpect(jsonPath("$.body.username").value(authSignUpResponse.getUsername()))
          .andExpect(jsonPath("$.body.publicId").value(authSignUpResponse.getPublicId()))
          .andExpect(
              jsonPath("$.body.userRole").value(authSignUpResponse.getUserRole().toString()));

      verify(authService, times(1)).signUp(any(AuthSingUpCommand.class));
    }
  }
}