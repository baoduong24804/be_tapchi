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

    /**
     * List of QuangCao entities associated with this Taikhoan.
     * Mapped by the 'taiKhoan' field in the QuangCao entity.
     * Cascade type is ALL and fetch type is EAGER.
     */
    @OneToMany(mappedBy = "taiKhoan", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    private List<QuangCao> quangCao;

    /**
     * List of Binhluan entities associated with this Taikhoan.
     * Mapped by the 'taiKhoan' field in the Binhluan entity.
     * Cascade type is ALL and fetch type is EAGER.
     */
    @OneToMany(mappedBy = "taiKhoan", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Binhluan> binhLuan;

    /**
     * List of Thich entities associated with this Taikhoan.
     * Mapped by the 'taiKhoan' field in the Thich entity.
     * Cascade type is ALL and fetch type is EAGER.
     */
    @OneToMany(mappedBy = "taiKhoan", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Thich> thich;

    /**
     * List of Baibao entities associated with this Taikhoan.
     * Mapped by the 'taiKhoan' field in the Baibao entity.
     * Cascade type is ALL and fetch type is EAGER.
     */
    @OneToMany(mappedBy = "taiKhoan", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Baibao> baiBao;
}