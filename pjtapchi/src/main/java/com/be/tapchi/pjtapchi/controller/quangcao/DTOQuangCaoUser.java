package com.be.tapchi.pjtapchi.controller.quangcao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DTOQuangCaoUser {
    private String quangcaoId;
    private String tieude;
    private String url;
    private String link;
    private String luotxem;
    private String luotclick;
    private String goiquangcao;
    private String ngaybd;
    private String ngaykt;
    private String status;
}
