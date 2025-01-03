package com.ed.authservice.auth.application.service;

import com.ed.authservice.auth.application.port.out.UserPersistencePort;
import com.ed.authservice.auth.domain.UserRole;
import com.ed.authservice.auth.application.port.in.AuthSingUpCommand;
import com.ed.authservice.auth.application.port.in.AuthUseCase;
import com.ed.authservice.auth.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService implements AuthUseCase {

    private final UserPersistencePort userPersistencePort;

    @Override
    public void signUp(AuthSingUpCommand authSingUpCommand) {
        User user = User.builder()
                .username(authSingUpCommand.getUsername())
                .password(authSingUpCommand.getPassword())
                .userRole(UserRole.DEFAULT_CUSTOMER)
                .build();

        userPersistencePort.saveUser(user);
    }

}