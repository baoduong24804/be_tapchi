package com.be.tapchi.pjtapchi.controller.danhmuc.model;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DTOCreateDanhMuc {
    private String danhmucId;
    @NotBlank(message = "Tiêu đề")
    private String tieude;
    private String mota;

    private String url;
    @NotBlank(message = "Tuần trống")
    private String tuan;
    @NotBlank(message = "Số trống")
    private String so;
    //private Integer status;
    //private LocalDate ngaytao;
    private String token;
}
