package com.be.tapchi.pjtapchi.controller.danhmuc.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DTOBinhluan {
    private String hovaten;
    private String noidung;
    private String thoigian;
}
