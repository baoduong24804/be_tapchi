package com.be.tapchi.pjtapchi.repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;

import com.be.tapchi.pjtapchi.model.TaikhoanToken;

public interface TaikhoanTKRepository extends JpaRepository<TaikhoanToken, Long> {
    TaikhoanToken findByToken(String token);
    void deleteByExpiryDateLessThan(LocalDateTime now);
}


