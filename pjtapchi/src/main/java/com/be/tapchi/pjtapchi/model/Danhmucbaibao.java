package com.be.tapchi.pjtapchi.model;

import java.util.List;

import com.be.tapchi.pjtapchi.model.Baibao;
import com.be.tapchi.pjtapchi.model.DanhMuc;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "danhmucbaibao")
public class Danhmucbaibao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "danhmucbaibao_id", nullable = false)
    private Integer id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "danhmuc_id")
    private DanhMuc danhmuc;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "baibao_id")
    private Baibao baibao;

}