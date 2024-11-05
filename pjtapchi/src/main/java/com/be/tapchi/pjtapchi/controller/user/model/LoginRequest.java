package com.be.tapchi.pjtapchi.controller.user.model;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginRequest {
    @NotNull(message = "Username không được để trống")
    private String username;

    @NotNull(message = "Password không được để trống")
    private String password;

   
    private String token;
}