package com.be.tapchi.pjtapchi.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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

    @OneToMany(mappedBy = "quangCao", cascade = CascadeType.ALL, fetch = FetchType.EAGER) // Khóa ngoại đến HopDong
    @JsonIgnore
    private List<HopDong> hopDong;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Taikhoan_id", nullable = false)
    private Taikhoan taiKhoan;
}
