package com.be.tapchi.pjtapchi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.be.tapchi.pjtapchi.model.Taikhoan;

public interface TaiKhoanRepository extends JpaRepository<Taikhoan,Long>{
    Taikhoan findByUsername(String username);

    boolean existsByUsername(String username);
}
