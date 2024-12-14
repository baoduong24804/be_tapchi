package com.be.tapchi.pjtapchi.controller.hoadon.DTO;

import java.util.Date;
import java.util.List;

public class HoaDonDTO {
    private Long hoadon_id;
    private Double tongTien;
    private Integer status;
    private Date ngayTao;
    private String orderCode;
    private HopDongDTO hopDong;

    // Getters and Setters

    public Long getHoadon_id() {
        return hoadon_id;
    }

    public void setHoadon_id(Long hoadon_id) {
        this.hoadon_id = hoadon_id;
    }

    public Double getTongTien() {
        return tongTien;
    }

    public void setTongTien(Double tongTien) {
        this.tongTien = tongTien;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(Date ngayTao) {
        this.ngayTao = ngayTao;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public HopDongDTO getHopDong() {
        return hopDong;
    }

    public void setHopDong(HopDongDTO hopDong) {
        this.hopDong = hopDong;
    }

    public static class HopDongDTO {
        private Long hopdong_id;
        private Date ngayBatDauHD;
        private Date ngayKetThucHD;
        private Integer status;
        private List<Object> bgqc;

        // Getters and Setters

        public Long getHopdong_id() {
            return hopdong_id;
        }

        public void setHopdong_id(Long hopdong_id) {
            this.hopdong_id = hopdong_id;
        }

        public Date getNgayBatDauHD() {
            return ngayBatDauHD;
        }

        public void setNgayBatDauHD(Date ngayBatDauHD) {
            this.ngayBatDauHD = ngayBatDauHD;
        }

        public Date getNgayKetThucHD() {
            return ngayKetThucHD;
        }

        public void setNgayKetThucHD(Date ngayKetThucHD) {
            this.ngayKetThucHD = ngayKetThucHD;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public List<Object> getBgqc() {
            return bgqc;
        }

        public void setBgqc(List<Object> bgqc) {
            this.bgqc = bgqc;
        }
    }
}