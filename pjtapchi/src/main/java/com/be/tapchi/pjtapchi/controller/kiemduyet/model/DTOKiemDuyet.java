package com.be.tapchi.pjtapchi.controller.kiemduyet.model;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DTOKiemDuyet {
    
    //private String taikhoanId;
    @NotBlank(message = "Không được để trống bài báo")
    private String baibaoId;

    @NotBlank(message = "Không được để trống tài khoản")
    private String taikhoanId;

    private String ghichu;
    //private LocalDate ngaykiemduyet;
    private Integer status;
    
    private String token;
}
