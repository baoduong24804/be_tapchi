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
    @Column(name = "taikhoan_hopdong_id")
    private Integer id;

    @Column(name = "tenhopdong")
    private String tenhopdong;

    @Column(name = "ngaymua")
    private LocalDate ngaymua;

    @Column(name = "status")
    private Integer status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "taikhoan_id")
    private Taikhoan taikhoan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hopdong_id")
    private HopDong hopdong;

}