package com.be.tapchi.pjtapchi.controller.author;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DTOBaibao {
    private String token;
    private String baibaoId;
    private String tieude;
    private String noidung;
    private String ngaytao;
    private String ngaydang;
    private String url;
    private String file;
    private String keywork;
    private String status;
    private String luotthich;
    private String luotxem;
    private DTOTheloai theloai;
    private String lichsu;
}
