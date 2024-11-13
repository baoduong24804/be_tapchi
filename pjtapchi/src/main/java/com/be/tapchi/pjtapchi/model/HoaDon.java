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
    private Long hoadonId;

    @Column(name = "tongtien", nullable = false)
    private Float tongTien;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "ngaytao", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date ngayTao;

    @Column(name = "paymentid", nullable = false)
    private String paymentId;

    @Column(name = "payerid", nullable = false)
    private String payerId;


    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hopdongId", nullable = false)
    private HopDong hopDong;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "taikhoan_id", nullable = false)
    private Taikhoan taikhoan;

}