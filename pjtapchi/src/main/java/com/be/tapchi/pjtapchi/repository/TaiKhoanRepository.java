package com.be.tapchi.pjtapchi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.be.tapchi.pjtapchi.model.Taikhoan;
import java.util.List;


public interface TaiKhoanRepository extends JpaRepository<Taikhoan,Long>{
    Taikhoan findByUsername(String username);

    Taikhoan findByGoogleId(String google_id);

    boolean existsByUsername(String username);

    Taikhoan findByEmailAndGoogleIdIsNull(String email);

    Taikhoan findBySdt(String sdt);

    boolean existsByEmail(String email);

    boolean existsBySdt(String sdt);

    boolean existsByGoogleId(String google_id);


}
