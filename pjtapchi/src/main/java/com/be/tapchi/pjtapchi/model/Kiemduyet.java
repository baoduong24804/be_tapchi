package com.be.tapchi.pjtapchi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "kiemduyet")
public class Kiemduyet {
    @Id
    @Column(name = "kiemduyet_id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "taikhoan_id")
    private Taikhoan taikhoan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "baibao_id")
    private Baibao baibao;

    @Lob
    @Column(name = "ghichu")
    private String ghichu;

    @Column(name = "ngaykiemduyet", nullable = false)
    private LocalDate ngaykiemduyet;

    @Column(name = "status", nullable = false)
    private Integer status;

}