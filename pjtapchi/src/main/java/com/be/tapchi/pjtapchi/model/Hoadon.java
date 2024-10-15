package com.be.tapchi.pjtapchi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "hoadon")
public class Hoadon {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hoadon_id_gen")
    @SequenceGenerator(name = "hoadon_id_gen", sequenceName = "hoadon_hoadon_id_seq", allocationSize = 1)
    @Column(name = "hoadon_id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "taikhoan_id")
    private Taikhoan taikhoan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hopdong_id")
    private Hopdong hopdong;

    @Column(name = "tongtien", nullable = false)
    private Double tongtien;

    @Column(name = "status", nullable = false)
    private Integer status;

    @Column(name = "ngaytao", nullable = false)
    private LocalDate ngaytao;

}