package com.be.tapchi.pjtapchi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.be.tapchi.pjtapchi.model.Taikhoan;
import java.util.List;

@Repository
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
