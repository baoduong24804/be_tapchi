package com.be.tapchi.pjtapchi.controller.admin.DTOUser;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DTOAdmin {
    private String token;
    private String status;
    private String taikhoanId;
    private String role;
    private String baibaoId;
}
