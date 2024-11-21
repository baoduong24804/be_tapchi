package com.be.tapchi.pjtapchi.controller.kiemduyet.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DTOResult {
    private String id;
    private String ghichu;
    private Integer status;
    private LocalDate ngaytao;
    private DTOBaiBao baiBao;

}
