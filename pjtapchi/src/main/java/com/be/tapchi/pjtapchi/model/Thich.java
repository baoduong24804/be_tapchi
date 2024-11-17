package com.be.tapchi.pjtapchi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Thich")
public class Thich {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Thich_id")
    private Long thichId;


    @Column(name = "Status")
    private Integer status;

    @Column(name = "Thoigianthich")
    private LocalDateTime thoigianthich;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Taikhoan_id")
    private Taikhoan taikhoan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Baibao_id")
    @JsonIgnore
    private Baibao baibao;

}
