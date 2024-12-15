package com.be.tapchi.pjtapchi.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
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

    @JsonIgnore
    @OneToMany(mappedBy = "bgqc", fetch = FetchType.LAZY)
    private Set<QuangCao> QuangCao;


//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "Taikhoan_id")
//    private Taikhoan taikhoan;


}