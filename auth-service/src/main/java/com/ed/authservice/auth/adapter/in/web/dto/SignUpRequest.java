package com.ed.authservice.auth.adapter.in.web.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignUpRequest {

    @NotNull(message = "Username cannot be null")
    @Size(min = 2, max = 20, message = "Username must be between 2 and 20 characters")
    @Pattern(
            regexp = "^[a-zA-Z0-9]+$",
            message = "Username must contain only English letters and numbers"
    )
    private String username;

    @NotNull(message = "Password cannot be null")
    @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Password must be at least 8 characters long and include uppercase, lowercase, number, and special character"
    )
    private String password;
}
