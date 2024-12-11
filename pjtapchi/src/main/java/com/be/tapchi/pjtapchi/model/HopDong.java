package com.be.tapchi.pjtapchi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "hopdong")
public class HopDong {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hopdong_id")
    private Long hopdong_id; // Chỉnh sửa tên biến cho nhất quán

    @Column(name = "ngaybatdauhd")
    private LocalDate ngayBatDauHD;

    @Column(name = "ngayketthuchd")
    private LocalDate ngayKetThucHD;

    @Column(name = "status")
    private int status;

    @OneToMany(mappedBy = "quangcao_id", fetch = FetchType.LAZY)
    private Set<QuangCao> QuangCao = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "banggiaqc_id")
    private Set<BangGiaQC> bgqc = new HashSet<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hoadon_id", nullable = false)
    private HoaDon hoaDon;
}
