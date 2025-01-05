package com.ed.authservice.auth.application.port.in;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AuthSignUpCommand {

  private String username;
  private String password;

  @Builder
  private AuthSignUpCommand(String username, String password) {
    this.username = username;
    this.password = password;
  }
}
