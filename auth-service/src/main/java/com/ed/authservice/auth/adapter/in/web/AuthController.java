package com.ed.authservice.auth.adapter.in.web;

import static com.ed.authservice.libs.common.ApiResponseUtils.created;

import com.ed.authservice.auth.adapter.in.web.dto.SignInRequest;
import com.ed.authservice.auth.adapter.in.web.dto.SignUpRequest;
import com.ed.authservice.auth.application.port.in.AuthSignInCommand;
import com.ed.authservice.auth.application.port.in.AuthSignUpCommand;
import com.ed.authservice.auth.application.port.in.AuthUseCase;
import com.ed.authservice.auth.application.port.out.AuthSignInResponse;
import com.ed.authservice.auth.application.port.out.AuthSignUpResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthUseCase authUseCase;

  @PostMapping("/sign-up")
  public ResponseEntity<AuthSignUpResponse> signUp(
      @Valid @RequestBody SignUpRequest signUpRequest) {
    return created(authUseCase.signUp(AuthSignUpCommand.builder()
        .username(signUpRequest.getUsername())
        .password(signUpRequest.getPassword())
        .build()));
  }

  @PostMapping("/sign-in")
  public AuthSignInResponse signIn(
      @Valid @RequestBody SignInRequest signInRequest) {
    return authUseCase.signIn(AuthSignInCommand.builder()
        .username(signInRequest.getUsername())
        .password(signInRequest.getPassword())
        .build());
  }
}
