package com.ed.authservice.auth.application.port.in;

public interface AuthUseCase {

    void signUp(AuthSingUpCommand authSingUpCommand);
}
