package com.be.tapchi.pjtapchi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "theloai")
public class Theloai {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "theloai_id_gen")
    @SequenceGenerator(name = "theloai_id_gen", sequenceName = "theloai_theloai_id_seq", allocationSize = 1)
    @Column(name = "theloai_id", nullable = false)
    private Integer id;

    @Column(name = "tenloai", nullable = false, length = 100)
    private String tenloai;

}