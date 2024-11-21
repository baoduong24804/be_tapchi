package com.be.tapchi.pjtapchi.controller.danhmuc.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DTOCreateDanhMuc {
    private String tieude;
    private String mota;
    private String url;
    private String tuan;
    private String so;
    private Integer status;
    private LocalDate ngaytao;
}
