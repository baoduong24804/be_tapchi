package com.be.tapchi.pjtapchi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "danhmucbaibao")
public class Danhmucbaibao {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "danhmucbaibao_id_gen")
    @SequenceGenerator(name = "danhmucbaibao_id_gen", sequenceName = "danhmucbaibao_danhmucbaibao_id_seq", allocationSize = 1)
    @Column(name = "danhmucbaibao_id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "danhmuc_id")
    private Danhmuc danhmuc;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "baibao_id")
    private Baibao baibao;

}