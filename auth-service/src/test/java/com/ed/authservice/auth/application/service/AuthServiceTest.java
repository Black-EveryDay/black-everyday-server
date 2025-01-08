package com.ed.authservice.auth.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.ed.authservice.auth.application.port.in.AuthSignInCommand;
import com.ed.authservice.auth.application.port.in.AuthSignUpCommand;
import com.ed.authservice.auth.application.port.out.AuthSignInResponse;
import com.ed.authservice.auth.application.port.out.AuthSignUpResponse;
import com.ed.authservice.auth.application.port.out.UserPersistencePort;
import com.ed.authservice.auth.domain.User;
import com.ed.authservice.auth.domain.UserRole;
import com.ed.authservice.libs.exception.AdapterException;
import com.ed.authservice.libs.exception.ExceptionStatus;
import com.ed.authservice.libs.exception.ServiceException;
import com.ed.authservice.libs.jwt.JwtUtil;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

  @Mock
  UserPersistencePort userPersistencePort;

  @Mock
  PasswordEncoder passwordEncoder;

  @Mock
  JwtUtil jwtUtil;

  @InjectMocks
  private AuthService authService;

  @Nested
  @DisplayName("SignUpTest")
  class SignUp {

    @Test
    @DisplayName("Should sign up success")
    void shouldSignUpSuccessTest() {
      //given
      String username = "test";
      String password = "Test12!@";
      String encodedPassword = "encodedPassword";

      UUID publicId = UUID.randomUUID();
      AuthSignUpCommand authSignUpCommand = AuthSignUpCommand.builder()
          .username(username)
          .password(password)
          .build();

      given(userPersistencePort.existsUser(authSignUpCommand.getUsername())).willReturn(false);

      given(userPersistencePort.saveUser(any(User.class))).willReturn(User.builder()
          .publicId(publicId.toString())
          .username(authSignUpCommand.getUsername())
          .userRole(UserRole.DEFAULT_CUSTOMER)
          .build());

      given(passwordEncoder.encode(password)).willReturn(encodedPassword);

      //when
      AuthSignUpResponse authSignUpResponse = authService.signUp(authSignUpCommand);

      //then
      assertEquals(authSignUpCommand.getUsername(), authSignUpResponse.getUsername());
      assertEquals(publicId.toString(), authSignUpResponse.getPublicId());
      assertEquals(UserRole.DEFAULT_CUSTOMER, authSignUpResponse.getUserRole());
    }

    @Test
    @DisplayName("Should sign up Fail")
    void shouldSignUpFailTest() {
      //given
      AuthSignUpCommand authSignUpCommand = AuthSignUpCommand.builder()
          .username("test")
          .password("Test12!@")
          .build();

      given(userPersistencePort.existsUser(authSignUpCommand.getUsername())).willReturn(true);

      //when-then
      ServiceException serviceException = assertThrows(ServiceException.class,
          () -> authService.signUp(authSignUpCommand));
      assertEquals(HttpStatus.CONFLICT, serviceException.getHttpStatus());
      assertEquals("Username already used", serviceException.getMessage());
    }
  }

  @Nested
  @DisplayName("SignInTest")
  class SignIn {

    @Test
    @DisplayName("Should sign in success")
    void shouldSignInSuccessTest() {
      //given
      String username = "test";
      String password = "Test12!@";
      String encodedPassword = "encodedPassword";

      AuthSignInCommand authSignInCommand = AuthSignInCommand.builder()
          .username(username)
          .password(password)
          .build();

      User user = User.builder()
          .id(1L)
          .username(username)
          .password(encodedPassword)
          .publicId(UUID.randomUUID().toString())
          .userRole(UserRole.DEFAULT_CUSTOMER)
          .build();

      given(userPersistencePort.findByUsername(authSignInCommand.getUsername())).willReturn(user);
      given(passwordEncoder.matches(authSignInCommand.getPassword(), user.getPassword()))
          .willReturn(true);
      given(jwtUtil.generateToken(user)).willReturn("token");

      //when
      AuthSignInResponse authSignInResponse = authService.signIn(authSignInCommand);

      //then
      assertEquals("token", authSignInResponse.getToken());
    }

    @Test
    @DisplayName("Should sign in fail when user not found")
    void shouldSignInFailWhenUserNotFoundTest() {
      //given
      AuthSignInCommand authSignInCommand = AuthSignInCommand.builder()
          .username("test")
          .password("Test12!@")
          .build();

      given(userPersistencePort.findByUsername(authSignInCommand.getUsername()))
          .willThrow(new AdapterException(ExceptionStatus.USER_NOT_FOUND));

      //when-then
      AdapterException adapterException = assertThrows(AdapterException.class,
          () -> authService.signIn(authSignInCommand));
      assertEquals(HttpStatus.NOT_FOUND, adapterException.getHttpStatus());
      assertEquals("user not found", adapterException.getMessage());
    }

    @Test
    @DisplayName("Should sign in fail when password not match")
    void shouldSignInFailWhenPasswordNotMatchTest() {
      //given
      AuthSignInCommand authSignInCommand = AuthSignInCommand.builder()
          .username("test")
          .password("Test12!@")
          .build();

      User user = User.builder()
          .id(1L)
          .username("test")
          .password("encodedPassword")
          .publicId(UUID.randomUUID().toString())
          .userRole(UserRole.DEFAULT_CUSTOMER)
          .build();

      given(userPersistencePort.findByUsername(authSignInCommand.getUsername())).willReturn(user);
      given(passwordEncoder.matches(authSignInCommand.getPassword(), user.getPassword()))
          .willReturn(false);

      //when-then
      ServiceException serviceException = assertThrows(ServiceException.class,
          () -> authService.signIn(authSignInCommand));
      assertEquals(HttpStatus.BAD_REQUEST, serviceException.getHttpStatus());
      assertEquals("Password not match", serviceException.getMessage());
    }
  }
}