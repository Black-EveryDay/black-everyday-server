package com.ed.authservice.auth.adapter.in.web;

import com.ed.authservice.auth.application.port.in.AuthSingUpCommand;
import com.ed.authservice.auth.application.port.in.AuthUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthUseCase authUseCase;

    @PostMapping("/sign-up")
    public void signUp(@RequestBody AuthSingUpCommand authSingUpCommand) {
        authUseCase.signUp(authSingUpCommand);
    }
}
