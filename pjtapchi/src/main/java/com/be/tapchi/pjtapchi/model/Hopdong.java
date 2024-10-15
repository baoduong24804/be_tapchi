package com.be.tapchi.pjtapchi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "hopdong")
public class Hopdong {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hopdong_id_gen")
    @SequenceGenerator(name = "hopdong_id_gen", sequenceName = "hopdong_hopdong_id_seq", allocationSize = 1)
    @Column(name = "hopdong_id", nullable = false)
    private Integer id;

    @Column(name = "ngaybatdauhd", nullable = false)
    private LocalDate ngaybatdauhd;

    @Column(name = "ngayketthuchd", nullable = false)
    private LocalDate ngayketthuchd;

    @Column(name = "status", nullable = false)
    private Integer status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quangcao_id")
    private Quangcao quangcao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "banggiaqc_id")
    private Banggiaqc banggiaqc;

}