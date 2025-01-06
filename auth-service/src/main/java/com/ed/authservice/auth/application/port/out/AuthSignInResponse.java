package com.ed.authservice.auth.application.port.out;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AuthSignInResponse {

  private final String token;

  @Builder
  private AuthSignInResponse(String token) {
    this.token = token;
  }
}
