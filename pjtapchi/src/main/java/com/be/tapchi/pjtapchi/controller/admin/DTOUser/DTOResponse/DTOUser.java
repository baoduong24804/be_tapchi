package com.be.tapchi.pjtapchi.controller.admin.DTOUser.DTOResponse;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DTOUser {
    private String taikhoanId;
    private String hovaten;
    private String username;
    private boolean google;
    private String sdt;
    private String email;
    private String url;
    private String status;
    private Set<DTORoles> roles;
}
