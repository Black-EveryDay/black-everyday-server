package com.ed.authservice.auth.application.port.in;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AuthSingUpCommand {

  private String username;
  private String password;

  @Builder
  private AuthSingUpCommand(String username, String password) {
    this.username = username;
    this.password = password;
  }
}
