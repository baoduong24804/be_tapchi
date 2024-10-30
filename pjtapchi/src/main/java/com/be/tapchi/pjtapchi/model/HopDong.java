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
    private Long hopDongId; // Chỉnh sửa tên biến cho nhất quán

    @Column(name = "ngaybatdauhd", nullable = false)
    private LocalDate ngayBatDauHD;

    @Column(name = "ngayketthuchd", nullable = false)
    private LocalDate ngayKetThucHD;

    @Column(name = "status", nullable = false)
    private int status;

    // @OneToMany(mappedBy = "quangcao_id", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    // private Set<QuangCao> QuangCao = new HashSet<>();

    // @OneToMany(fetch = FetchType.EAGER)
    // @JoinColumn(name = "banggiaqc_id", nullable = false, insertable = false, updatable = false)
    // private Set<BangGiaQC> bangGiaQC = new HashSet<>();

    // @OneToOne(fetch = FetchType.EAGER)
    // @JoinColumn(name = "hoadon_id", nullable = false)
    // private HoaDon hoaDon;
}
