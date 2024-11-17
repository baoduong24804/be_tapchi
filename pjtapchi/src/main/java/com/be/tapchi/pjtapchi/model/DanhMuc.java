package com.be.tapchi.pjtapchi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

    @Column(name = "tieude")
    private String tieuDe;

    @Column(name = "mota")
    private String moTa;

    @Column(name = "url")
    private String url;

    @Column(name = "status")
    private Integer status;

    @Column(name = "ngaytao")
    @Temporal(TemporalType.DATE)
    private Date ngayTao;

    @Column(name = "tuan")
    private Integer tuan;

    @Column(name = "so")
    private Integer so;

    @JsonIgnore
    @OneToMany(mappedBy = "danhmuc", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Danhmucbaibao> danhmucbaibaos;

    // @OneToMany(mappedBy = "baibao", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    // private List<Baibao> baibaos;


}