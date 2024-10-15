package com.be.tapchi.pjtapchi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "hopdong")
public class HopDong {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "HopDong_id")
    private Long hopDongId;

    @Column(name = "TenHopDong", nullable = false, length = 255)
    private String tenHopDong;

    @Column(name = "NgayKy")
    private Date ngayKy;

    @Column(name = "NgayKetThuc")
    private Date ngayKetThuc;

    @Column(name = "TongGiaTri", nullable = false)
    private Double tongGiaTri;

    @OneToMany(mappedBy = "hopDong", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<QuangCao> quangCaoList; // Mối quan hệ 1-N với QuangCao
}
