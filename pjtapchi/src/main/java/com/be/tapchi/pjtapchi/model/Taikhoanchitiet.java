package com.be.tapchi.pjtapchi.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "taikhoanchitiet")
public class Taikhoanchitiet {

    @Id
    @Column(name = "taikhoan_id")
    private Long taikhoan_id;

    @Column(name = "hovaten")
    private String hovaten;

    
    @Column(name = "sdt")
    private String sdt;

    @Column(name = "email")
    private String email;

    @Column(name = "url")
    private String url;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "ngaytao")
    private Date ngaytao;

    @Column(name = "status")
    private Integer status;

    @JsonIgnore
    @OneToOne
    @MapsId
    @JoinColumn(name = "taikhoan_id")
    private Taikhoan taikhoan;

}