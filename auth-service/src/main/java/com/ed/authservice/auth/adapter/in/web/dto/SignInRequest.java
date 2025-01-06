package com.ed.authservice.auth.adapter.in.web.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SignInRequest {

  private final String username;
  private final String password;

  @Builder
  private SignInRequest(String username, String password) {
    this.username = username;
    this.password = password;
  }
}
