package com.be.tapchi.pjtapchi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "danhmuc")
public class Danhmuc {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "danhmuc_id_gen")
    @SequenceGenerator(name = "danhmuc_id_gen", sequenceName = "danhmuc_danhmuc_id_seq", allocationSize = 1)
    @Column(name = "danhmuc_id", nullable = false)
    private Integer id;

    @Column(name = "tieude", nullable = false)
    private String tieude;

    @Column(name = "mota")
    private String mota;

    @Column(name = "url")
    private String url;

    @Column(name = "status", nullable = false)
    private Integer status;

    @Column(name = "ngaytao", nullable = false)
    private LocalDate ngaytao;

}