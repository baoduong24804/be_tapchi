package com.be.tapchi.pjtapchi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "vaitro")
public class Vaitro {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vaitro_id_gen")
    @SequenceGenerator(name = "vaitro_id_gen", sequenceName = "vaitro_vaitro_id_seq", allocationSize = 1)
    @Column(name = "vaitro_id", nullable = false)
    private Integer id;

    @Column(name = "tenrole", nullable = false, length = 100)
    private String tenrole;

}