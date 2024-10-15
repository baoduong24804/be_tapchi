package com.be.tapchi.pjtapchi.model;

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
@Getter
@Setter
@Entity
@Table(name = "binhluan")
public class Binhluan {

    /**
     * The unique identifier for the comment.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "binhluan_id_gen")
    @SequenceGenerator(name = "binhluan_id_gen", sequenceName = "binhluan_binhluan_id_seq", allocationSize = 1)
    @Column(name = "binhluan_id", nullable = false)
    private Integer id;

    /**
     * The account (Taikhoan) associated with the comment.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "taikhoan_id")
    private Taikhoan taikhoan;

    /**
     * The article (Baibao) associated with the comment.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "baibao_id")
    private Baibao baibao;

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
}