// package com.be.tapchi.pjtapchi.model;

// import java.sql.Date;

// import jakarta.persistence.*;
// import lombok.AllArgsConstructor;
// import lombok.Getter;
// import lombok.NoArgsConstructor;
// import lombok.Setter;

// @Entity
// @Getter
// @Setter
// @AllArgsConstructor
// @NoArgsConstructor
// @Table(name = "QuangCao")
// public class QuangCao {

// @Id
// @GeneratedValue(strategy = GenerationType.IDENTITY)
// private Long quangCaoId;

// @Column(nullable = false, length = 255)
// private String tieuDe;

// @Column(nullable = false, columnDefinition = "TEXT")
// private String url;

// @Column(nullable = false)
// private int status;

// @Column(nullable = false)
// @Temporal(TemporalType.DATE)
// private Date ngayBatDau;

// @Column
// @Temporal(TemporalType.DATE)
// private Date ngayKetThuc;

// @ManyToOne(fetch = FetchType.LAZY)
// @JoinColumn(name = "taiKhoan_id", nullable = false)
// private Taikhoan taiKhoan;

// }
