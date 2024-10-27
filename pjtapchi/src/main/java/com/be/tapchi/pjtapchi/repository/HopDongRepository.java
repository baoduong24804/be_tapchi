package com.be.tapchi.pjtapchi.repository;

import com.be.tapchi.pjtapchi.model.HopDong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HopDongRepository extends JpaRepository<HopDong, Long> {
}
