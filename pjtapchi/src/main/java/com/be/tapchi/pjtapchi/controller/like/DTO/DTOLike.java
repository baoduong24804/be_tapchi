package com.be.tapchi.pjtapchi.controller.like.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DTOLike {
    private String token;
    private String baibaoId;
    private String status;
}
