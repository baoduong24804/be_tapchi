package com.be.tapchi.pjtapchi.repository;

import com.be.tapchi.pjtapchi.model.HoaDon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface HoaDonRepository extends JpaRepository<HoaDon, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE HoaDon h SET h.status = :status WHERE h.hoadonId = :id")
    int updateStatusById(@Param("id") Long id, @Param("status") int status);

}
