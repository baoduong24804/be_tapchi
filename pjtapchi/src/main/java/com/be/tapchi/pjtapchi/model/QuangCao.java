package com.be.tapchi.pjtapchi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "quangcao")
public class QuangCao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quangcao_id")
    private Long quangcao_id;

    @Column(name = "tieude")
    private String tieude;

    @Column(name = "url")
    private String url;

    @Column(name = "status")
    private int status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Taikhoan_id")
    private Taikhoan taikhoan;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "Hopdong_id")
//    private HopDong hopDong;
}