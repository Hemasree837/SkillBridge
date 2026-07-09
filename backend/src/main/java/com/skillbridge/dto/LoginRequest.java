package com.skillbridge.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * Data received from the frontend when a user logs in.
 */
@Data
public class LoginRequest {

    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;
}
