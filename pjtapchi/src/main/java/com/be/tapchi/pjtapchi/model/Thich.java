package com.be.tapchi.pjtapchi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Thich")
public class Thich {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Thich_id")
    private Long thichId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "TaiKhoan_id", nullable = false)
    private Taikhoan taiKhoan;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "BaiBao_id", nullable = false)
    private Baibao baibao;

    @Column(name = "ThoiGianThich", nullable = false)
    private LocalDateTime thoiGianThich;


}
