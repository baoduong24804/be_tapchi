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
    // @Query("SELECT d FROM DanhMuc d WHERE d.ngaytao BETWEEN :startOfWeek AND
    // :endOfWeek")
    // Page<DanhMuc> findDanhmucByWeek(
    // @Param("startOfWeek") LocalDate startOfWeek,
    // @Param("endOfWeek") LocalDate endOfWeek,
    // Pageable pageable
    // );
    // @Query("SELECT d FROM DanhMuc d WHERE d.ngaytao BETWEEN :startOfWeek AND
    // :endOfWeek AND d.danhmucbaibaos.baibao.status = :status")
    // Page<DanhMuc> findDanhmucByWeekAndStatus(
    // @Param("startOfWeek") LocalDate startOfWeek,
    // @Param("endOfWeek") LocalDate endOfWeek,
    // @Param("status") Integer status,
    // Pageable pageable
    // );

    List<DanhMuc> findByTuanAndSo(Integer tuan, Integer so);

    @Query("SELECT d FROM DanhMuc d LEFT JOIN d.danhmucbaibaos b WHERE d.ngaytao BETWEEN :startOfWeek AND :endOfWeek")
    Page<DanhMuc> findDanhmucByWeekAndStatus(
            @Param("startOfWeek") LocalDate startOfWeek,
            @Param("endOfWeek") LocalDate endOfWeek,
            Pageable pageable);

    // @Query("SELECT b.danhmucbaibaos.danhmuc FROM Baibao b WHERE LOWER(b.keyword)
    // LIKE LOWER(CONCAT('%', :keyword, '%'))")
    // Page<DanhMuc> findDanhmucByKeyword(@Param("keyword") String keyword, Pageable
    // pageable);

    @Query("SELECT d.danhmuc FROM Baibao b JOIN b.danhmucbaibaos d WHERE LOWER(b.keyword) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<DanhMuc> findDanhmucByKeyword(@Param("keyword") String keyword, Pageable pageable);

}
