package com.be.tapchi.pjtapchi.model;

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
    @JoinColumn(name = "danhmuc_id", insertable = false, updatable = false)
    private DanhMuc danhmuc;

    @Column(name = "danhmuc_id", insertable = false, updatable = false)
    private Integer danhmucId;

    @Transient
    private String tieuDe;

    @Transient
    private Integer tuan;

    @Transient
    private Integer so;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "baibao_id")
    private Baibao baibao;

    @Column(name = "baibao_id", insertable = false, updatable = false)
    private Integer baobaiId;

    @PostLoad
    private void postLoad() {
        if (danhmuc != null) {
            this.tieuDe = danhmuc.getTieuDe();
            this.tuan = danhmuc.getTuan();
            this.so = danhmuc.getSo();
        }
    }
}