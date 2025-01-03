package com.ed.authservice.auth.application.port.in;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AuthSingUpCommand {
    private String username;
    private String password;
}
