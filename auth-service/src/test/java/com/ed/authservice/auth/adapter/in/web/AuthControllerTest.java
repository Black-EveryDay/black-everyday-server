package com.ed.authservice.auth.adapter.in.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ed.authservice.auth.adapter.in.web.dto.SignInRequest;
import com.ed.authservice.auth.adapter.in.web.dto.SignUpRequest;
import com.ed.authservice.auth.application.port.in.AuthSignInCommand;
import com.ed.authservice.auth.application.port.in.AuthSignUpCommand;
import com.ed.authservice.auth.application.port.in.AuthUseCase;
import com.ed.authservice.auth.application.port.out.AuthSignInResponse;
import com.ed.authservice.auth.application.port.out.AuthSignUpResponse;
import com.ed.authservice.auth.domain.UserRole;
import com.ed.authservice.libs.exception.AdapterException;
import com.ed.authservice.libs.exception.ExceptionStatus;
import com.ed.authservice.libs.exception.ServiceException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(AuthController.class)
class AuthControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockitoBean
  private JpaMetamodelMappingContext jpaMetamodelMappingContext;

  @MockitoBean
  private AuthUseCase authUseCase;

  @Nested
  @DisplayName("SignUpTest")
  class SignUp {

    @Test
    @DisplayName("Should sign up success")
    void shouldSignUpSuccess() throws Exception {
      //given
      final String uri = "/api/v1/auth/sign-up";

      final SignUpRequest signUpRequest = SignUpRequest.builder()
          .username("test1")
          .password("Test@123")
          .build();

      final AuthSignUpResponse authSignUpResponse = AuthSignUpResponse.builder()
          .username("test1")
          .publicId(UUID.randomUUID().toString())
          .userRole(UserRole.DEFAULT_CUSTOMER)
          .build();

      given(authUseCase.signUp(any(AuthSignUpCommand.class))).willReturn(authSignUpResponse);

      //when
      ResultActions resultActions = mockMvc.perform(post(uri)
          .contentType(MediaType.APPLICATION_JSON)
          .content(objectMapper.writeValueAsString(signUpRequest)));

      //then
      resultActions.andExpect(status().isCreated())
          .andExpect(jsonPath("$.success").value(true))
          .andExpect(jsonPath("$.body.username").value(authSignUpResponse.getUsername()))
          .andExpect(jsonPath("$.body.publicId").value(authSignUpResponse.getPublicId()))
          .andExpect(
              jsonPath("$.body.userRole").value(authSignUpResponse.getUserRole().toString()));

      verify(authUseCase, times(1)).signUp(any(AuthSignUpCommand.class));
    }

    @ParameterizedTest
    @CsvSource({
        "t, Test@123, Username must be between 2 and 20 characters",
        "testtesttesttesttest1, Test@123, Username must be between 2 and 20 characters",
        "testtesttesttest가, Test@123, Username must contain only English letters and numbers",
        "test1, Te@1, Password must be between 8 and 20 characters",
        "test1, Test1234, 'Password must be at least 8 characters long and include uppercase, lowercase, number, and special character'",
        "test1, Test!@!@, 'Password must be at least 8 characters long and include uppercase, lowercase, number, and special character'",
        "test1, test12!@, 'Password must be at least 8 characters long and include uppercase, lowercase, number, and special character'",
        "test1, TEST1234, 'Password must be at least 8 characters long and include uppercase, lowercase, number, and special character'",
    })
    @DisplayName("Should sign up Fail when username length under two")
    void shouldFailTest(String userName, String passWord, String errorMessage)
        throws Exception {
      //given
      final String uri = "/api/v1/auth/sign-up";

      final SignUpRequest signUpRequest = SignUpRequest.builder()
          .username(userName)
          .password(passWord)
          .build();

      given(authUseCase.signUp(any(AuthSignUpCommand.class))).willThrow(new ServiceException(
          ExceptionStatus.USERNAME_ALREADY_USED));

      //when
      ResultActions resultActions = mockMvc.perform(post(uri)
          .contentType(MediaType.APPLICATION_JSON)
          .content(objectMapper.writeValueAsString(signUpRequest)));

      //then
      resultActions.andExpect(status().isBadRequest())
          .andExpect(jsonPath("$.success").value(false))
          .andExpect(
              jsonPath("$.body.message").value(errorMessage));
    }
  }

  @Nested
  @DisplayName("SignInTest")
  class SignIn {

    @Test
    @DisplayName("Should sign in success")
    void shouldSignInSuccess() throws Exception {
      //given
      final String uri = "/api/v1/auth/sign-in";

      final SignInRequest signInRequest = SignInRequest.builder()
          .username("test1")
          .password("Test@123")
          .build();

      final String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJhdXRoLXNlcnZpY2UiLCJpYXQiOjE3MzYxNDgzMDQsImV4cCI6MTgyMjU0ODMwNCwidXNlcklkIjoiZDFhMzU0ZDMtMWQ1Yi00YzE3LWI3MjctOGIzNTFjOWRhZTYzIiwidXNlclJvbGUiOiJERUZBVUxUX0NVU1RPTUVSIn0.9_adct0nTs87ft543pXb_mYGy8fyy2yDZ2HECRPNoAqoCr8ku47gHWSwovGfxnIvqVd8rEJxA-JfRksC4Vbg1w";

      final AuthSignInResponse authSignInResponse = AuthSignInResponse.builder()
          .token(token)
          .build();

      given(authUseCase.signIn(any(AuthSignInCommand.class))).willReturn(authSignInResponse);

      //when
      ResultActions resultActions = mockMvc.perform(post(uri)
          .contentType(MediaType.APPLICATION_JSON)
          .content(objectMapper.writeValueAsString(signInRequest)));

      //then
      resultActions.andExpect(status().isOk())
          .andExpect(jsonPath("$.success").value(true))
          .andExpect(jsonPath("$.body.token").value(token));

      verify(authUseCase, times(1)).signIn(any(AuthSignInCommand.class));
    }

    @Test
    @DisplayName("Should sign in Fail when cannot found user by username")
    void shouldSignInFailWhenCannotFoundUserByUsername() throws Exception {
      //given
      final String uri = "/api/v1/auth/sign-in";

      final SignInRequest signInRequest = SignInRequest.builder()
          .username("test1")
          .password("Test@123")
          .build();

      given(authUseCase.signIn(any(AuthSignInCommand.class)))
          .willThrow(new AdapterException(ExceptionStatus.USER_NOT_FOUND));

      //when
      ResultActions resultActions = mockMvc.perform(post(uri)
          .contentType(MediaType.APPLICATION_JSON)
          .content(objectMapper.writeValueAsString(signInRequest)));

      //then
      resultActions.andExpect(status().isNotFound())
          .andExpect(jsonPath("$.success").value(false))
          .andExpect(jsonPath("$.body.message").value("user not found"));

      verify(authUseCase, times(1)).signIn(any(AuthSignInCommand.class));
    }

    @Test
    @DisplayName("Should sign in Fail when password not match")
    void shouldSignInFailWhenPasswordNotMatch() throws Exception {
      //given
      final String uri = "/api/v1/auth/sign-in";

      final SignInRequest signInRequest = SignInRequest.builder()
          .username("test1")
          .password("Test@123")
          .build();

      given(authUseCase.signIn(any(AuthSignInCommand.class)))
          .willThrow(new ServiceException(ExceptionStatus.USER_PASSWORD_NOT_MATCH));

      //when
      ResultActions resultActions = mockMvc.perform(post(uri)
          .contentType(MediaType.APPLICATION_JSON)
          .content(objectMapper.writeValueAsString(signInRequest)));

      //then
      resultActions.andExpect(status().isBadRequest())
          .andExpect(jsonPath("$.success").value(false))
          .andExpect(jsonPath("$.body.message").value("Password not match"));

      verify(authUseCase, times(1)).signIn(any(AuthSignInCommand.class));
    }
  }
}