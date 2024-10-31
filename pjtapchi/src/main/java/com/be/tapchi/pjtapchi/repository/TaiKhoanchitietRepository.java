package com.be.tapchi.pjtapchi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.be.tapchi.pjtapchi.model.Taikhoan;
import com.be.tapchi.pjtapchi.model.Taikhoanchitiet;

public interface TaiKhoanchitietRepository extends JpaRepository<Taikhoanchitiet,Long>{

    boolean existsByEmail(String email);

    Taikhoanchitiet findByEmail(String email);
    
}
