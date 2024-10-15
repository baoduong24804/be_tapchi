package com.be.tapchi.pjtapchi.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import java.time.LocalDate;

/**
 * Represents the Kiemduyet entity.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "kiemduyet")
public class Kiemduyet {

    /**
     * The unique identifier for the Kiemduyet entity.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "kiemduyet_id_gen")
    @SequenceGenerator(name = "kiemduyet_id_gen", sequenceName = "kiemduyet_kiemduyet_id_seq", allocationSize = 1)
    @Column(name = "kiemduyet_id", nullable = false)
    private Integer id;

    /**
     * The associated Taikhoan entity.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "taikhoan_id")
    private Taikhoan taikhoan;

    /**
     * The associated Baibao entity.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "baibao_id")
    private Baibao baibao;

    /**
     * Notes or comments related to the Kiemduyet entity.
     */
    @Column(name = "ghichu", length = Integer.MAX_VALUE)
    private String ghichu;

    /**
     * The date when the Kiemduyet was performed.
     */
    @Column(name = "ngaykiemduyet", nullable = false)
    private LocalDate ngaykiemduyet;

    /**
     * The status of the Kiemduyet.
     */
    @Column(name = "status", nullable = false)
    private Integer status;
}