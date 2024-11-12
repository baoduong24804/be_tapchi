package com.be.tapchi.pjtapchi.repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.be.tapchi.pjtapchi.model.Taikhoan;
import com.be.tapchi.pjtapchi.model.TaikhoanToken;
import java.util.List;

@Repository
public interface TaikhoanTKRepository extends JpaRepository<TaikhoanToken, Long> {
    TaikhoanToken findByToken(String token);
    void deleteByExpiryDateLessThan(LocalDateTime now);
    TaikhoanToken findByTaikhoan(Taikhoan taikhoan);
    List<TaikhoanToken> findByExpiryDate(LocalDateTime now);
    List<TaikhoanToken> findByExpiryDateBefore(LocalDateTime dateTime);
    List<TaikhoanToken> findByExpiryDateAfter(LocalDateTime dateTime);
    
}


