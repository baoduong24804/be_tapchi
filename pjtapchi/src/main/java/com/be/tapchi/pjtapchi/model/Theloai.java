package com.be.tapchi.pjtapchi.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * Represents a category (Theloai) entity.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "theloai")
public class Theloai {

    /**
     * The unique identifier for the category.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "theloai_id_gen")
    @SequenceGenerator(name = "theloai_id_gen", sequenceName = "theloai_theloai_id_seq", allocationSize = 1)
    @Column(name = "theloai_id", nullable = false)
    private Integer id;

    /**
     * The name of the category.
     */
    @Column(name = "tenloai", nullable = false, length = 100)
    private String tenloai;
}