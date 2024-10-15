package com.be.tapchi.pjtapchi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "quangcao")
public class QuangCao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "QuangCao_id")
    private Long quangCaoId;

    @Column(name = "TenQuangCao", nullable = false, length = 255)
    private String tenQuangCao;

    @Column(name = "MoTa", length = 255)
    private String moTa;

    @Column(name = "HinhAnh", length = 255)
    private String hinhAnh;

    @Column(name = "NgayBatDau")
    private Date ngayBatDau;

    @Column(name = "NgayKetThuc")
    private Date ngayKetThuc;

    @ManyToOne
    @JoinColumn(name = "HopDong_id", nullable = false)
    private HopDong hopDong; // Mối quan hệ với HopDong
}
