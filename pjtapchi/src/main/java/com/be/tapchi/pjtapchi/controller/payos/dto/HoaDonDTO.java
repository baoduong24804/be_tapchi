package com.be.tapchi.pjtapchi.controller.payos.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
@Getter
@Setter
public class HoaDonDTO {
    private Long hoadon_id;
    private Float tongTien;
    private Integer status;
    private Date ngayTao;
    private String orderCode;
    private Long hopdong_id;
    private Long taikhoan_id;

}
