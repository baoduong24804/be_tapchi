package com.be.tapchi.pjtapchi.controller.user.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ResetPassword {

    
    private String token;

    private String newpassword;
}