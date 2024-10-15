package com.be.tapchi.pjtapchi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "binhluan")
public class Binhluan {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "binhluan_id_gen")
    @SequenceGenerator(name = "binhluan_id_gen", sequenceName = "binhluan_binhluan_id_seq", allocationSize = 1)
    @Column(name = "binhluan_id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "taikhoan_id")
    private Taikhoan taikhoan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "baibao_id")
    private Baibao baibao;

    @Column(name = "thoigianbl", nullable = false)
    private Instant thoigianbl;

    @Lob
    @Column(name = "noidung", nullable = false)
    private String noidung;

    @Column(name = "status", nullable = false)
    private Integer status;

}