package com.be.tapchi.pjtapchi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Theloai {

    /**
     * The unique identifier for the category.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "theloai_id")
    private Integer id;

    /**
     * The name of the category.
     */
    @Column(name = "tenloai")
    private String tenloai;

    @OneToMany(mappedBy = "theloai", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Baibao> baibaos;

    public Theloai(Integer id) {
        this.id = id;
    }

}