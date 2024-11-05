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

    @Column(name = "Thoigianthich", nullable = false)
    private LocalDateTime thoigianthich;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Taikhoan_id", nullable = false)
    private Taikhoan taikhoan;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Baibao_id", nullable = false)
    @JsonIgnore
    private Baibao baibao;

}
