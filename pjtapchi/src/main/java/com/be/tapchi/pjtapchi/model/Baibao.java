package com.be.tapchi.pjtapchi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

/**
 * Represents a Baibao entity.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "baibao")
public class Baibao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "baibao_id", nullable = false)
    private Integer id;

    @Column(name = "tieude", nullable = false)
    private String tieude;

    @Column(name = "noidung")
    private String noidung;

    @Column(name = "ngaydang")
    private LocalDate ngaydang;

    @Column(name = "url")
    private String url;

    @Column(name = "status")
    private Integer status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "theloai_id")
    private Theloai theloai;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Taikhoan_id")
    private Taikhoan taikhoan;

    @OneToMany(mappedBy = "baibao", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Binhluan> binhluans;

    @OneToMany(mappedBy = "baibao", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Thich> thichs;
    
    @OneToMany(mappedBy = "baibao", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Danhmucbaibao> danhmucbaibaos;


//    @OneToMany(mappedBy = "baibao", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<Danhmucbaibao> danhmucbaibaos;
}