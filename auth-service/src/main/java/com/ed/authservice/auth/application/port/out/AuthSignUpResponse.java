package com.ed.authservice.auth.application.port.out;

import com.ed.authservice.auth.domain.UserRole;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AuthSignUpResponse {

  private final String publicId;
  private final String username;
  private final UserRole userRole;

  @Builder
  private AuthSignUpResponse(String publicId, String username, UserRole userRole) {
    this.publicId = publicId;
    this.username = username;
    this.userRole = userRole;
  }
}
