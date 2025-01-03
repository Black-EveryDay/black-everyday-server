package com.ed.authservice.auth.domain;

import lombok.Builder;

public class User {

  private final String id;
  private final String username;
  private final String password;
  private final UserRole userRole;

  @Builder
  private User(String id, String username, String password, UserRole userRole) {
    this.id = id;
    this.username = username;
    this.password = password;
    this.userRole = userRole;
  }

  public String getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public UserRole getUserRole() {
    return userRole;
  }
}
