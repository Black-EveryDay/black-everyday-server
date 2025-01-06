package com.ed.authservice.auth.application.port.in;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AuthSignInCommand {

  private final String username;
  private final String password;

  @Builder
  private AuthSignInCommand(String username, String password) {
    this.username = username;
    this.password = password;
  }

}
