package com.be.tapchi.pjtapchi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.be.tapchi.pjtapchi.model.BangGiaQC;

@Repository
public interface BangGiaQCRepository extends JpaRepository<BangGiaQC, Long> {
    
}
