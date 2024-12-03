package com.be.tapchi.pjtapchi.controller.danhmuc.model;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DTOBaiBaoDM {
    private String baibaoId;
    private String tieude;
    private String noidung;
    private String url;
    private String file;
    private String status;
    private String keyword;
    private LocalDate ngaytao;
    private LocalDate ngaydang;
    private DTOTaiKhoanDM taikhoan;
    private DTOTheLoaiDM theloai;
    private DTOThich thich;
    private List<DTOBinhluan> binhluans;
    
}
