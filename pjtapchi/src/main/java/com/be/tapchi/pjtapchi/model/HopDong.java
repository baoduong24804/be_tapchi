package com.be.tapchi.pjtapchi.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "hopdong")
public class HopDong {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hopdong_id")
    private Long hopDongId; // Chỉnh sửa tên biến cho nhất quán

    @Column(name = "ngaybatdauhd")
    private LocalDate ngayBatDauHD;

    @Column(name = "ngayketthuchd")
    private LocalDate ngayKetThucHD;

    @Column(name = "status")
    private int status;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "quangcao_id", nullable = false, insertable = false, updatable = false)
    private Set<QuangCao> QuangCao = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "banggiaqc_id")
    private BangGiaQC bangGiaQC;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hoadon_id", nullable = false)
    private HoaDon hoaDon;

    public HopDong(Integer hdId) {
        hopDongId = hdId.longValue();
    }
}
