package com.be.tapchi.pjtapchi.controller.danhmuc.model;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DTOBaiBaoDanhMuc {
    private String danhmucId;
    private String tieude;
    private String mota;
    private String url;
    private String tuan;
    private String so;
    private LocalDate ngaytao;
    private String status;
    private List<DTOBaiBaoDM> baibao;
}
