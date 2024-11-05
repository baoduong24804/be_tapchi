package com.be.tapchi.pjtapchi.controller.user.model;
import java.util.Set;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserResponse {
    private Set<String> roles;
   
    private String token;
}