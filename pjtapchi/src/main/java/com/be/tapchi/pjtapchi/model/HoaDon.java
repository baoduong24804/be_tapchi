package com.be.tapchi.pjtapchi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

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

    @Column(name = "tongtien", nullable = false)
    private Float tongTien;

    @Column(name = "status", nullable = false)
    private Integer status;

    @Column(name = "ngaytao", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date ngayTao;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hopdong_id", nullable = false)
    private HopDong hopDong;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hoadon", nullable = false)
    private Taikhoan taikhoan;
}