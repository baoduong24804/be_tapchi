package com.be.tapchi.pjtapchi.controller.baibao.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KiemduyetED {
private String id;
private TaikhoanED taikhoan;
private String ghichu;
private Integer status;
}