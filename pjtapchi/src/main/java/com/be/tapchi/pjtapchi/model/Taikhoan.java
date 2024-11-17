package com.be.tapchi.pjtapchi.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "taikhoan")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Taikhoan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Taikhoan_id")
    private Long taikhoan_id;

    @JsonIgnore
    @Column(name = "Username", length = 100)
    private String username;

    @JsonIgnore
    @Column(name = "Password", length = 100)
    private String password;

    @Column(name = "hovaten")
    private String hovaten;

    @Column(name = "Google_ID")
    private String googleId;

    @Column(name = "sdt")
    private String sdt;

    @Column(name = "email")
    private String email;

    @Column(name = "url")
    private String url;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @Column(name = "ngaytao")
    private Date ngaytao;

    @Column(name = "status")
    private Integer status;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "taikhoan_vaitro", joinColumns = @JoinColumn(name = "taikhoan_id"), inverseJoinColumns = @JoinColumn(name = "vaitro_id"))
    private Set<Vaitro> vaitro = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "taikhoan")
    @JsonIgnore
    private List<QuangCao> quangcao;

    // @OneToMany(mappedBy = "taikhoan", cascade = CascadeType.ALL)
    // @JsonIgnore
    // private List<DanhMuc> danhmuc;
    //
    // @OneToMany(mappedBy = "taikhoan", cascade = CascadeType.ALL)
    // @JsonIgnore
    // private List<BangGiaQC> banggiaqc;
    //

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "taikhoan")
    @JsonIgnore
    private List<Baibao> dsbaibao;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "taikhoan")
    @JsonIgnore
    private List<HoaDon> hoadon;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "taikhoan")
    @JsonIgnore
    private List<Binhluan> binhluans;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "taikhoan")
    @JsonIgnore
    private List<Thich> thichs;


    // @OneToMany(fetch = FetchType.EAGER, mappedBy = "taikhoan")
    // @JsonIgnore
    // private List<TaikhoanHopdong> taikhoanHopdongs;

}