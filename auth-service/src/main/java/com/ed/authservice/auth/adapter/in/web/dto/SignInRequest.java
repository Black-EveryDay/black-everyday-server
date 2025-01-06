package com.ed.authservice.auth.adapter.in.web.dto;

import lombok.Getter;

@Getter
public class SignInRequest {

  private String username;
  private String password;
}
