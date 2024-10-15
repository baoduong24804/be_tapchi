package com.be.tapchi.pjtapchi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "taikhoan")
public class Taikhoan {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "taikhoan_id_gen")
    @SequenceGenerator(name = "taikhoan_id_gen", sequenceName = "taikhoan_taikhoan_id_seq", allocationSize = 1)
    @Column(name = "taikhoan_id", nullable = false)
    private Integer id;

    @Column(name = "username", nullable = false, length = 100)
    private String username;

    @Column(name = "password", nullable = false, length = 100)
    private String password;

}