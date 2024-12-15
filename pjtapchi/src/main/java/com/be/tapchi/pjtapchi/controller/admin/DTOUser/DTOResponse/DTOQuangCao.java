package com.be.tapchi.pjtapchi.controller.admin.DTOUser.DTOResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DTOQuangCao {
    private String quangcaoId;
    private String tieude;
    private String url;
    private String status;
    private String link;
    private String ngaybd;
    private String ngaykt;
    private DTOTaikhoan taikhoan;
    private DTOGoiQC bgqc;
}
