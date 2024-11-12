package com.be.tapchi.pjtapchi.controller.user.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterByGG {

   
    //private int[] roles;
    @NotBlank(message = "Name không được để trống")
    private String name;

    @NotBlank(message = "Sub không được để trống")
    private String sub;

    @NotBlank(message = "Email không được để trống")
    private String email;

    @NotBlank(message = "Picture không được để trống")
    private String picture;

    private boolean verified_email;

    // @NotNull(message = "Ngày tạo không được để trống")
    // private Date ngaytao;
}
