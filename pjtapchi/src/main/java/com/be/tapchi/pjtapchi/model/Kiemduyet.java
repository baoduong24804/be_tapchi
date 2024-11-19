package com.be.tapchi.pjtapchi.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDate;

/**
 * Represents the Kiemduyet entity.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "kiemduyet")
public class Kiemduyet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "kiemduyet_id")
    private Integer id;


    @Column(name = "ghichu") // Change to an appropriate length if necessary
    private String ghichu;

    @Column(name = "ngaykiemduyet")
    private LocalDate ngaykiemduyet;

    @Column(name = "status")
    private Integer status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "taikhoan_id")
    private Taikhoan taikhoan;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "baibao_id")
    private Baibao baibao;

}
