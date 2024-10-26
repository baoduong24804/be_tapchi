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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "kiemduyet_id_gen")
    @SequenceGenerator(name = "kiemduyet_id_gen", sequenceName = "kiemduyet_kiemduyet_id_seq", allocationSize = 1)
    @Column(name = "kiemduyet_id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "taikhoan_id") // Ensure this column exists in the database
    @JsonIgnore // Prevent serialization
    private Taikhoan taikhoan;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "baibao_id") // Ensure this column exists in the database
    @JsonIgnore // Prevent serialization
    private Baibao baibao;

    @Column(name = "ghichu", length = 255) // Change to an appropriate length if necessary
    private String ghichu;

    @Column(name = "ngaykiemduyet", nullable = false)
    private LocalDate ngaykiemduyet;

    @Column(name = "status", nullable = false)
    private Integer status;
}
