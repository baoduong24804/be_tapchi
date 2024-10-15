package com.be.tapchi.pjtapchi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "taikhoanchitiet")
public class Taikhoanchitiet {
    @Id
    @Column(name = "taikhoan_id", nullable = false)
    private Integer id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "taikhoan_id", nullable = false)
    private Taikhoan taikhoan;

    @Column(name = "hovaten", nullable = false)
    private String hovaten;

    @Column(name = "sdt", nullable = false, length = 15)
    private String sdt;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "url")
    private String url;

    @Column(name = "ngaytao", nullable = false)
    private LocalDate ngaytao;

    @Column(name = "status", nullable = false)
    private Integer status;

}