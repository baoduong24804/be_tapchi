package com.be.tapchi.pjtapchi.controller.admin.DTOUser.DTOResponse;

import java.util.List;
import java.util.Set;

import com.be.tapchi.pjtapchi.controller.baibao.model.KiemduyetED;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DTOBaibao {
    // private String taikhoanId;
    // private String hovaten;
    private String id;
    private String tieude;
    private String noidung;
    private String ngaytao;
    private String ngaydang;
    private String url;
    private String file;
    private String status;
    private String luotxem;
    private String luotthich;
    private DTOTheloai theloai;
    private DTOTaikhoan taikhoan;
    private List<KiemduyetED> kiemduyet;
}
