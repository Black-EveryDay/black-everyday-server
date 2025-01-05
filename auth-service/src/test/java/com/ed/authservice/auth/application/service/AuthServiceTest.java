package com.ed.authservice.auth.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.ed.authservice.auth.application.port.in.AuthSingUpCommand;
import com.ed.authservice.auth.application.port.out.AuthSignUpResponse;
import com.ed.authservice.auth.application.port.out.UserPersistencePort;
import com.ed.authservice.auth.domain.User;
import com.ed.authservice.auth.domain.UserRole;
import com.ed.authservice.libs.exception.ServiceException;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

  @Mock
  UserPersistencePort userPersistencePort;
  @InjectMocks
  private AuthService authService;

  @Test
  @DisplayName("Should sign up success")
  void shouldSignUpSuccessTest() {
    //given
    UUID publicId = UUID.randomUUID();
    AuthSingUpCommand authSingUpCommand = AuthSingUpCommand.builder()
        .username("test")
        .password("Test12!@")
        .build();

    given(userPersistencePort.existsUser(authSingUpCommand.getUsername())).willReturn(false);

    given(userPersistencePort.saveUser(any(User.class))).willReturn(User.builder()
        .publicId(publicId.toString())
        .username(authSingUpCommand.getUsername())
        .userRole(UserRole.DEFAULT_CUSTOMER)
        .build());

    //when
    AuthSignUpResponse authSignUpResponse = authService.signUp(authSingUpCommand);

    //then
    assertEquals(authSingUpCommand.getUsername(), authSignUpResponse.getUsername());
    assertEquals(publicId.toString(), authSignUpResponse.getPublicId());
    assertEquals(UserRole.DEFAULT_CUSTOMER, authSignUpResponse.getUserRole());
  }

  @Test

  @DisplayName("Should sign up Fail")
  void shouldSignUpFailTest() {
    //given
    AuthSingUpCommand authSingUpCommand = AuthSingUpCommand.builder()
        .username("test")
        .password("Test12!@")
        .build();

    given(userPersistencePort.existsUser(authSingUpCommand.getUsername())).willReturn(true);

    //when-then
    ServiceException serviceException = assertThrows(ServiceException.class,
        () -> authService.signUp(authSingUpCommand));
    assertEquals(HttpStatus.CONFLICT, serviceException.getHttpStatus());
    assertEquals("Username already used", serviceException.getMessage());
  }
}