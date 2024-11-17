package com.be.tapchi.pjtapchi.controller.user.model;

import java.util.Date;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegister {

    private String url_d = "https://anime404.click/api/files/view/ac0dc7e2-6135-48f5-ad85-340d21097697_user-avatar-4-icon-511x512-wlu19u1d.png";
    //private int[] roles;
    @NotBlank(message = "Username không được để trống")
    private String username;

    @NotBlank(message = "Password không được để trống")
    private String password;

    @NotBlank(message = "Họ và tên không được để trống")
    private String hovaten;

    @NotBlank(message = "Số điện thoại không được để trống")
    private String sdt;

    //@NotBlank(message = "URL không được để trống")
    private String url = url_d;

    @NotBlank(message = "Email không được để trống")
    private String email;

    // @NotNull(message = "Ngày tạo không được để trống")
    // private Date ngaytao;
}
