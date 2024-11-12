package com.be.tapchi.pjtapchi.repository;

import java.time.LocalDate;
import java.util.List;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.be.tapchi.pjtapchi.model.DanhMuc;

@Repository
public interface DanhMucRepository extends JpaRepository<DanhMuc, Long> {
    @Query("SELECT d FROM DanhMuc d WHERE d.ngayTao BETWEEN :startOfWeek AND :endOfWeek")
    Page<DanhMuc> findDanhmucByWeek(
            @Param("startOfWeek") LocalDate startOfWeek, 
            @Param("endOfWeek") LocalDate endOfWeek,
            Pageable pageable
    );
}
