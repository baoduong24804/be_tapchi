package com.be.tapchi.pjtapchi.controller.payos.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
@Getter
@Setter
public class HopDongDTO {
    private Long hopdong_id;
    private Integer Status;
    private Integer hoaDon_id;
    private Integer quangcao_id;
    private Integer banggiaqc_id;
    private Date ngaybatdauhd;
    private Date ngayketthuchd;
}
