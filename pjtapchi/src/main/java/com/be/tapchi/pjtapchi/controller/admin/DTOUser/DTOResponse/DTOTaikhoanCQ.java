package com.be.tapchi.pjtapchi.controller.admin.DTOUser.DTOResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DTOTaikhoanCQ {
    private String taikhoanId;
    private String hovaten;
    private String email;
    private String status;
}