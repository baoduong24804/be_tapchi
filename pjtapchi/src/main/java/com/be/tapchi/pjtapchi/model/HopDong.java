package com.be.tapchi.pjtapchi.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
    private Long hopdong_id; // Chỉnh sửa tên biến cho nhất quán

    @Column(name = "ngaybatdauhd")
    private Date ngayBatDauHD;

    @Column(name = "ngayketthuchd")
    private Date ngayKetThucHD;

    @Column(name = "status")
    private int status;

    @OneToMany(mappedBy = "hopDong", fetch = FetchType.LAZY)
    private Set<QuangCao> QuangCao;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hoadon_id", nullable = false)
    private HoaDon hoaDon;
}
