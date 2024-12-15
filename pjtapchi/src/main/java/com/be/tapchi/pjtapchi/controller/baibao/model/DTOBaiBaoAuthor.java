package com.be.tapchi.pjtapchi.controller.baibao.model;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DTOBaiBaoAuthor {
    private String id;
    private String tieude;
    private String noidung;
    private String tukhoa;
    private String ngaytao;
    private String ngaydang;
    private Integer status;
    private String file;
    private String url;
    private String luotxem;
    private String luotthich;
    private String luotcmt;
    private String lichsu;
    private String tendanhmuc;
    private List<KiemduyetED> kiemduyet;
    //private String token;
    
    



}
