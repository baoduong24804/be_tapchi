package com.be.tapchi.pjtapchi.controller.kiemduyet.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DTOKiemDuyet {
    private String taikhoanId;
    private String baibaoId;
    private String ghichu;
    //private LocalDate ngaykiemduyet;
    private Integer status;
    private String token;
}
