package com.ed.authservice.auth.application.service;

import com.ed.authservice.auth.application.port.in.AuthSignUpCommand;
import com.ed.authservice.auth.application.port.in.AuthUseCase;
import com.ed.authservice.auth.application.port.out.AuthSignUpResponse;
import com.ed.authservice.auth.application.port.out.UserPersistencePort;
import com.ed.authservice.auth.domain.User;
import com.ed.authservice.auth.domain.UserRole;
import com.ed.authservice.libs.exception.ExceptionStatus;
import com.ed.authservice.libs.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService implements AuthUseCase {

  private final UserPersistencePort userPersistencePort;

  @Override
  public AuthSignUpResponse signUp(AuthSignUpCommand authSignUpCommand) {

    if (userPersistencePort.existsUser(authSignUpCommand.getUsername())) {
      throw new ServiceException(ExceptionStatus.USERNAME_ALREADY_USED);
    }

    User user = User.builder().username(authSignUpCommand.getUsername())
        .password(authSignUpCommand.getPassword()).userRole(UserRole.DEFAULT_CUSTOMER).build();

    User savedUser = userPersistencePort.saveUser(user);

    return AuthSignUpResponse.builder()
        .publicId(savedUser.getPublicId())
        .username(savedUser.getUsername())
        .userRole(savedUser.getUserRole())
        .build();
  }

}
