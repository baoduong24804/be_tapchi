package com.be.tapchi.pjtapchi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Represents a comment (Binhluan) entity.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "binhluan")
public class Binhluan {

    /**
     * The unique identifier for the comment.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "binhluan_id")
    private Integer id;


    /**
     * The article (Baibao) associated with the comment.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "baibao_id")
    @JsonIgnore
    private Baibao baibao;

    /**
     * The timestamp when the comment was made.
     */
    @Column(name = "thoigianbl")
    private LocalDateTime thoigianbl;

    /**
     * The content of the comment.
     */
    @Column(name = "noidung")
    private String noidung;

    /**
     * The status of the comment.
     */
    @Column(name = "status")
    private Integer status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Taikhoan_id")
    private Taikhoan taikhoan;

    // @ManyToOne(fetch = FetchType.EAGER)
    // @JoinColumn(name = "baibao_id", nullable = false)
    // private Baibao baibao;

}