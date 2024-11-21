package com.be.tapchi.pjtapchi.controller.kiemduyet.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DTOUpdate {
    private String token;
    private String kiemduyetId;
    private String status;
    private String ghichu;
    
}
