package com.be.tapchi.pjtapchi.controller.bgqc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DTOBGQC {
    private String bgqcId;
    private String songay;
    private String giatien;
    private String tengoi;
    private boolean conqc;
}
