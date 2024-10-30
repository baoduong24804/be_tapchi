package com.be.tapchi.pjtapchi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "taikhoan_hopdong")
public class TaikhoanHopdong {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "taikhoan_hopdong_id", nullable = false)
    private Integer id;

    @Column(name = "tenhopdong", nullable = false)
    private String tenhopdong;

    @Column(name = "ngaymua", nullable = false)
    private LocalDate ngaymua;

    @Column(name = "status", nullable = false)
    private Integer status;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "taikhoan_id")
    private Taikhoan taikhoan;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "hopdong_id")
    private HopDong hopdong;

}