package com.be.tapchi.pjtapchi.controller.danhmuc.model;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DTODanhMucBaiBao2 {
    private String danhmucId;
    private String tieude;
    private String mota;
    private String url;
    private String status;
    private LocalDate ngaytao;
    private String tuan;
    private String so;
    private List<DTOBaiBaoDM> baibaos;
}
