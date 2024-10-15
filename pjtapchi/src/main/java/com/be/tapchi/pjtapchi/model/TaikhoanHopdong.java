package com.be.tapchi.pjtapchi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "taikhoan_hopdong")
public class TaikhoanHopdong {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "taikhoan_hopdong_id_gen")
    @SequenceGenerator(name = "taikhoan_hopdong_id_gen", sequenceName = "taikhoan_hopdong_taikhoan_hopdong_id_seq", allocationSize = 1)
    @Column(name = "taikhoan_hopdong_id", nullable = false)
    private Integer id;

    @Column(name = "tenhopdong", nullable = false)
    private String tenhopdong;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "taikhoan_id")
    private Taikhoan taikhoan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hopdong_id")
    private Hopdong hopdong;

    @Column(name = "ngaymua", nullable = false)
    private LocalDate ngaymua;

    @Column(name = "status")
    private Integer status;

}