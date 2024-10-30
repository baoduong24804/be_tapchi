package com.be.tapchi.pjtapchi;

import com.be.tapchi.pjtapchi.model.HopDong;
import com.be.tapchi.pjtapchi.model.Taikhoan;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "taikhoan_hopdong")
public class TaikhoanHopdong {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "taikhoan_hopdong_id_gen")
    @SequenceGenerator(name = "taikhoan_hopdong_id_gen", sequenceName = "taikhoan_hopdong_taikhoan_hopdong_id_seq", allocationSize = 1)
    @Column(name = "taikhoan_hopdong_id", nullable = false)
    private Integer id;

    @Column(name = "tenhopdong", nullable = false)
    private String tenhopdong;

    @Column(name = "ngaymua", nullable = false)
    private LocalDate ngaymua;

    @Column(name = "status", nullable = false)
    private Integer status;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "taikhoan_id")
    private Taikhoan taikhoan;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "hopdong_id")
    private HopDong hopdong;

}