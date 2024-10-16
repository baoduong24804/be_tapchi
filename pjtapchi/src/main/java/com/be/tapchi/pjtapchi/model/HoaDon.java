package com.be.tapchi.pjtapchi.model;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "hoadon")
public class HoaDon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hoadon_id")
    private Long hoaDonId;

    @ManyToOne
    @JoinColumn(name = "taikhoan_id")
    private Taikhoan taiKhoan;

    @ManyToOne
    @JoinColumn(name = "hopdong_id")
    private HopDong hopDong;

    @Column(name = "tongtien", nullable = false)
    private Float tongTien;

    @Column(name = "status", nullable = false)
    private Integer status;

    @Column(name = "ngaytao", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date ngayTao;
}