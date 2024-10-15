package com.be.tapchi.pjtapchi.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Thich")
public class Thich {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Thich_id")
    private Long thichId;

    @ManyToOne
    @JoinColumn(name = "TaiKhoan_id", nullable = false)
    private Taikhoan taiKhoan;

    //@ManyToOne
    //@JoinColumn(name = "BaiBao_id", nullable = false)
    //private BaiBao baiBao;

    @Column(name = "ThoiGianThich", nullable = false)
    private LocalDateTime thoiGianThich;
}
