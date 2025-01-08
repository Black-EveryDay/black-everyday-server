package com.ed.authservice.auth.application.port.in;

import com.ed.authservice.auth.application.port.out.AuthSignInResponse;
import com.ed.authservice.auth.application.port.out.AuthSignUpResponse;

public interface AuthUseCase {

  AuthSignUpResponse signUp(AuthSignUpCommand authSignUpCommand);

  AuthSignInResponse signIn(AuthSignInCommand build);
}
