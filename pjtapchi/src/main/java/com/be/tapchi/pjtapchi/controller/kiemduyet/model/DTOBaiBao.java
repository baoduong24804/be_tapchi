package com.be.tapchi.pjtapchi.controller.kiemduyet.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DTOBaiBao {
    private String id;
    private String tieude;
    private LocalDate ngaytao;
    private DTOTaiKhoan taiKhoan;
    private DTOTheLoai theLoai;
}
