// package com.be.tapchi.pjtapchi.model;

<<<<<<< HEAD
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "quangcao_id", nullable = false)
    private QuangCao quangCao; 

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "banggiaqc_id", nullable = false)
    private BangGiaQC bangGiaQC;
}
=======
// import jakarta.persistence.*;
// import lombok.AllArgsConstructor;
// import lombok.Getter;
// import lombok.NoArgsConstructor;
// import lombok.Setter;

// import java.util.Date;

// @Entity
// // @Getter
// // @Setter
// // @AllArgsConstructor
// // @NoArgsConstructor
// @Table(name = "HopDong")
// public class HopDong {

// @Id
// @GeneratedValue(strategy = GenerationType.IDENTITY)
// private Long hopDongId;

// @Column(nullable = false)
// @Temporal(TemporalType.DATE)
// private Date ngayBatDauHD;

// @Column(nullable = false)
// @Temporal(TemporalType.DATE)
// private Date ngayKetThucHD;

// @Column(nullable = false)
// private int status;

// @ManyToOne(fetch = FetchType.LAZY)
// @JoinColumn(name = "quangCao_id", nullable = false)
// private QuangCao quangCao;

// @ManyToOne(fetch = FetchType.LAZY)
// @JoinColumn(name = "bangGiaQC_id", nullable = false)
// //private BangGiaQC bangGiaQC;
// }
>>>>>>> 1e2e8fb85b2398c376c179e772d9108fd6292c8c
