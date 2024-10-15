package com.be.tapchi.pjtapchi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "baibao")
public class Baibao {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "baibao_id_gen")
    @SequenceGenerator(name = "baibao_id_gen", sequenceName = "baibao_baibao_id_seq", allocationSize = 1)
    @Column(name = "baibao_id", nullable = false)
    private Integer id;

    @Column(name = "tieude", nullable = false)
    private String tieude;

    @Column(name = "noidung")
    private String noidung;

    @Column(name = "ngaydang", nullable = false)
    private LocalDate ngaydang;

    @Column(name = "url")
    private String url;

    @Column(name = "status", nullable = false)
    private Integer status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "theloai_id")
    private Theloai theloai;

}