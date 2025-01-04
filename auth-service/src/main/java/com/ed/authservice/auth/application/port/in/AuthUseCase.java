package com.ed.authservice.auth.application.port.in;

import com.ed.authservice.auth.application.port.out.AuthSignUpResponse;

public interface AuthUseCase {

  AuthSignUpResponse signUp(AuthSingUpCommand authSingUpCommand);
}
