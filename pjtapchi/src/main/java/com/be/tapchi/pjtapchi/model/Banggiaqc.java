package com.be.tapchi.pjtapchi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "banggiaqc")
public class Banggiaqc {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "banggiaqc_id_gen")
    @SequenceGenerator(name = "banggiaqc_id_gen", sequenceName = "banggiaqc_banggiaqc_id_seq", allocationSize = 1)
    @Column(name = "banggiaqc_id", nullable = false)
    private Integer id;

    @Column(name = "songay", nullable = false)
    private Integer songay;

    @Column(name = "giatien", nullable = false)
    private Double giatien;

    @Column(name = "tengoi", nullable = false, length = 100)
    private String tengoi;

}