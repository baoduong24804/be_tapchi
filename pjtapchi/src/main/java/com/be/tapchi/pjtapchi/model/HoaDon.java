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

    @Column(name = "tongtien")
    private Float tongTien;

    @Column(name = "status")
    private String status;

    @Column(name = "ngaytao")
    @Temporal(TemporalType.DATE)
    private Date ngayTao;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hopdongId")
    private HopDong hopDong;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "taikhoan_id")
    private Taikhoan taikhoan;

}