// package com.be.tapchi.pjtapchi.model;

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
