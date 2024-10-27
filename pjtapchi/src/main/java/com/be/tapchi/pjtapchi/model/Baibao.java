package com.be.tapchi.pjtapchi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Represents a Baibao entity.
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "baibao")

public class Baibao {

    /**
     * The unique identifier for the Baibao.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "baibao_id", nullable = false)
    private Integer id;
    /**
     * The title of the Baibao.
     */
    @Column(name = "tieude", nullable = false)
    private String tieude;
    /**
     * The content of the Baibao.
     */
    @Column(name = "noidung")
    private String noidung;
    /**
     * The date the Baibao was published.
     */
    @Column(name = "ngaydang", nullable = false)
    private LocalDate ngaydang;
    /**
     * The URL of the Baibao.
     */
    @Column(name = "url")
    private String url;
    /**
     * The status of the Baibao.
     */
    @Column(name = "status", nullable = false)
    private Integer status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "theloai_id", nullable = false)
    private Theloai theloai;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Taikhoan_id", nullable = false)
    private Taikhoan taikhoan;
}
