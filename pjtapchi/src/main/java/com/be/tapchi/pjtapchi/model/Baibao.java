package com.be.tapchi.pjtapchi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
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
    @Column(name = "baibao_id")
    private Integer id;

    @Column(name = "tieude")
    private String tieude;

    @Column(name = "noidung")
    private String noidung;

    @Column(name = "ngaydang")
    private LocalDate ngaydang;

    @Column(name = "ngaytao")
    private LocalDate ngaytao;

    @Column(name = "url")
    private String url;

    @Column(name = "file")
    private String file;

    @Column(name = "status")
    private Integer status;
    
    @Column(name = "keyword")
    private String keyword;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "theloai_id")
    private Theloai theloai;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Taikhoan_id")
    private Taikhoan taikhoan;


    @JsonIgnore
    @OneToMany(mappedBy = "baibao", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Binhluan> binhluans;

    @JsonIgnore
    @OneToMany(mappedBy = "baibao", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Thich> thichs;

    
    @JsonIgnore
    @OneToMany(mappedBy = "baibao", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Danhmucbaibao> danhmucbaibaos;

    @JsonIgnore
    @OneToMany(mappedBy = "baibao", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Kiemduyet> kiemduyets;

    // @OneToMany(mappedBy = "baibao", cascade = CascadeType.ALL, fetch =
    // FetchType.LAZY)
    // private List<Danhmucbaibao> danhmucbaibaos;

}