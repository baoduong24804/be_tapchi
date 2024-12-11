package com.be.tapchi.pjtapchi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "hoadon")
public class HoaDon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hoadon_id")
    private Long hoadon_id;

    @Column(name = "tongtien")
    private Float tongTien;

    @Column(name = "status")
    private Integer status;

    @Column(name = "ngaytao")
    @Temporal(TemporalType.DATE)
    private Date ngayTao;

    @Column(name = "paymentid")
    private String paymentId;

    @Column(name = "payerid")
    private String payerId;

    @Column(name = "ngaythanhtoan")
    private LocalDateTime ngaythanhtoan;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hopdong_id")
    private HopDong hopDong;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Taikhoan_id")
    private Taikhoan taikhoan;

}