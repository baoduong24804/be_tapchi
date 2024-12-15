package com.be.tapchi.pjtapchi.controller.hopdong.DTO;

import com.be.tapchi.pjtapchi.model.BangGiaQC;
import com.be.tapchi.pjtapchi.model.HopDong;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContractResponseDTO {
    private HopDong hopDong;
    private BangGiaQC bangGiaQC;
}