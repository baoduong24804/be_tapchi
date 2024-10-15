package com.be.tapchi.pjtapchi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "taikhoan_vaitro")
public class TaikhoanVaitro {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mavaitro")
    private Vaitro mavaitro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "taikhoan_id")
    private Taikhoan taikhoan;

}