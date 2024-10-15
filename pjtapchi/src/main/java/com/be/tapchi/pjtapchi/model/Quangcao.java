package com.be.tapchi.pjtapchi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "quangcao")
public class Quangcao {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "quangcao_id_gen")
    @SequenceGenerator(name = "quangcao_id_gen", sequenceName = "quangcao_quangcao_id_seq", allocationSize = 1)
    @Column(name = "quangcao_id", nullable = false)
    private Integer id;

    @Column(name = "tieude", nullable = false)
    private String tieude;

    @Lob
    @Column(name = "url", nullable = false)
    private String url;

    @Column(name = "status", nullable = false)
    private Integer status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "taikhoan_id")
    private Taikhoan taikhoan;

}