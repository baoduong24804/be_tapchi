package com.be.tapchi.pjtapchi.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "banggiaqc")
public class BangGiaQC {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "banggiaqc_id")
    private Long banggiaqc_id;

    @Column(name = "songay")
    private Integer songay;

    @Column(name = "giatien")
    private Float giatien;

    @Column(name = "tengoi")
    private String tengoi;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Taikhoan_id")
    private Taikhoan taikhoan;


}