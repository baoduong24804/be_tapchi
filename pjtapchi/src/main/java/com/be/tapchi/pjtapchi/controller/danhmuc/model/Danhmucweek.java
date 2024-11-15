package com.be.tapchi.pjtapchi.controller.danhmuc.model;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class Danhmucweek {
    //@NotBlank(message = "Username không được để trống")
    private Integer page;

    //@NotBlank(message = "Password không được để trống")
    private Integer size;

   
    //private String token;

}