package com.be.tapchi.pjtapchi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "danhmuc")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DanhMuc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "danhmuc_id")
    private Long danhmucId;

    @Column(name = "tieude", nullable = false, length = 255)
    private String tieuDe;

    @Column(name = "mota", length = 255)
    private String moTa;

    @Column(name = "url", length = 255)
    private String url;

    @Column(name = "status", nullable = false)
    private Integer status;

    @Column(name = "ngaytao", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date ngayTao;

    @Column(name = "tuan", nullable = false)
    private Integer tuan;

    @Column(name = "so", nullable = false)
    private Integer so;


    @OneToMany(mappedBy = "danhmuc", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Danhmucbaibao> danhmucbaibaos;

    // @OneToMany(mappedBy = "baibao", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    // private List<Baibao> baibaos;


}