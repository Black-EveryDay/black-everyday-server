package com.ed.authservice.auth.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class User {

  private final Long id;
  private final String publicId;
  private final String username;
  private final String password;
  private final UserRole userRole;

  @Builder
  private User(Long id, String publicId, String username, String password, UserRole userRole) {
    this.id = id;
    this.publicId = publicId;
    this.username = username;
    this.password = password;
    this.userRole = userRole;
  }
}
