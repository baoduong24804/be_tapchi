package com.be.tapchi.pjtapchi.controller.danhmuc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DTOAddBBDM {
    private String baibaoId;
    private String danhmucId;
    private String token;
}
