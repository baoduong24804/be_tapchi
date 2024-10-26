package com.be.tapchi.pjtapchi.model;

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



    @Column(name = "ThoiGianThich", nullable = false)
    private LocalDateTime thoiGianThich;


}
