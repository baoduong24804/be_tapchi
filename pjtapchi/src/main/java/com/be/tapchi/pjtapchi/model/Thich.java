package com.be.tapchi.pjtapchi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "thich")
public class Thich {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "thich_id_gen")
    @SequenceGenerator(name = "thich_id_gen", sequenceName = "thich_thich_id_seq", allocationSize = 1)
    @Column(name = "thich_id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "taikhoan_id")
    private Taikhoan taikhoan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "baibao_id")
    private Baibao baibao;

    @Column(name = "thoigianthich", nullable = false)
    private Instant thoigianthich;

}