package com.be.tapchi.pjtapchi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private Long danhMucId;

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


}