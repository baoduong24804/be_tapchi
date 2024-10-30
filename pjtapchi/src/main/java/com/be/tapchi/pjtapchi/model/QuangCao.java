package com.be.tapchi.pjtapchi.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
    private Long quangCaoId;

    @Column(nullable = false, length = 255, name = "tieude")
    private String tieuDe;

    @Column(nullable = false, columnDefinition = "TEXT", name = "url")
    private String url;

    @Column(nullable = false, name = "status")
    private int status;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Taikhoan_id", nullable = false)
    private Taikhoan taikhoan;

    // @OneToMany(mappedBy = "QuangCao", fetch = FetchType.LAZY)
    // @JsonIgnore
    // private List<HopDong> HopDongs;


}
