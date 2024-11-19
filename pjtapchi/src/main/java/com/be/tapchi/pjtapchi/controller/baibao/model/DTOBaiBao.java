package com.be.tapchi.pjtapchi.controller.baibao.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DTOBaiBao {
    private String theloaiID;
    private String tieude;
    private String noidung;
    private String tukhoa;
    private String file;
    private String url;
    private String token;
}
