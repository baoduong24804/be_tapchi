package com.be.tapchi.pjtapchi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.be.tapchi.pjtapchi.model.HoaDon;

@Repository
public interface HoaDonRepository extends JpaRepository<HoaDon, Long> {
    
}
