package com.be.tapchi.pjtapchi.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @OneToMany(mappedBy = "taiKhoan", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    private List<QuangCao> quangCao;
}