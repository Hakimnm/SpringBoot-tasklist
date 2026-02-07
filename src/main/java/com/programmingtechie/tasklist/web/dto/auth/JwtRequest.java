package com.programmingtechie.tasklist.web.dto.auth;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class JwtRequest {
    @NotNull(message = "username must be not null")
    private String username;
    @NotNull(message = "Password mus be not null")
    private String password;
}
