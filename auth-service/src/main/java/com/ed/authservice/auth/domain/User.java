package com.ed.authservice.auth.domain;

import lombok.Builder;

public class User {
    private String id;
    private String username;
    private String password;
    private UserRole userRole;

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
