package com.be.tapchi.pjtapchi.controller.user.model;

import java.util.Date;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegister {

   
    //private int[] roles;
    @NotNull(message = "Username không được để trống")
    private String username;

    @NotNull(message = "Password không được để trống")
    private String password;

    @NotNull(message = "Họ và tên không được để trống")
    private String hovaten;

    @NotNull(message = "Số điện thoại không được để trống")
    private String sdt;

    @NotNull(message = "URL không được để trống")
    private String url;

    @NotNull(message = "Email không được để trống")
    private String email;

    // @NotNull(message = "Ngày tạo không được để trống")
    // private Date ngaytao;
}
