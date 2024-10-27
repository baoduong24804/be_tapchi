package com.be.tapchi.pjtapchi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "taikhoan")
public class Taikhoan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Taikhoan_id")
    private Long taikhoan_id;

    @Column(name = "Username", nullable = false, unique = true, length = 100)
    private String username;

    @Column(name = "Password", nullable = false, length = 100)
    private String password;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "taikhoan_vaitro",
            joinColumns = @JoinColumn(name = "taikhoan_id"),
            inverseJoinColumns = @JoinColumn(name = "vaitro_id")
    )
    private Set<Vaitro> vaitro = new HashSet<>();

    @OneToMany(mappedBy = "taikhoan", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<QuangCao> quangcao;

    //    @OneToMany(mappedBy = "taikhoan", cascade = CascadeType.ALL)
//    @JsonIgnore
//    private List<DanhMuc> danhmuc;
//
//    @OneToMany(mappedBy = "taikhoan", cascade = CascadeType.ALL)
//    @JsonIgnore
//    private List<BangGiaQC> banggiaqc;
//
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "taikhoan")
    @JsonIgnore
    private List<Baibao> dsbaibao;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "taikhoan")
    @JsonIgnore
    private List<HoaDon> hoadon;

}