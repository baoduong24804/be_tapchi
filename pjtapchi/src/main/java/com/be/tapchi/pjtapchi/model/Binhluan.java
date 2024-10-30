package com.be.tapchi.pjtapchi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;

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
    @Column(name = "binhluan_id", nullable = false)
    private Integer id;

    /**
     * The timestamp when the comment was made.
     */
    @Column(name = "thoigianbl", nullable = false)
    private Instant thoigianbl;

    /**
     * The content of the comment.
     */
    @Column(name = "noidung", nullable = false, length = Integer.MAX_VALUE)
    private String noidung;

    /**
     * The status of the comment.
     */
    @Column(name = "status", nullable = false)
    private Integer status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Taikhoan_id", nullable = false)
    private Taikhoan taikhoan;


    // @ManyToOne(fetch = FetchType.EAGER)
    // @JoinColumn(name = "baibao_id", nullable = false)
    // private Baibao baibao;

}