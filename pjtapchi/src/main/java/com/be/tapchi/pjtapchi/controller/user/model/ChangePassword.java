package com.be.tapchi.pjtapchi.controller.user.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ChangePassword {
    @NotNull(message = "Username không được để trống")
    private String username;

    @NotNull(message = "Password không được để trống")
    private String password;

    @NotNull(message = "NewPassword không được để trống")
    private String newpassword;
}