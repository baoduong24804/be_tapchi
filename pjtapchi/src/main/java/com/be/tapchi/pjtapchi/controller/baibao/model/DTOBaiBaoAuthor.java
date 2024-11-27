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
    private LocalDate ngaytao;
    private LocalDate ngaydang;
    private Integer status;
    private String file;
    private String url;
    private List<KiemduyetAT> kiemduyet;
    //private String token;
    
    



}
