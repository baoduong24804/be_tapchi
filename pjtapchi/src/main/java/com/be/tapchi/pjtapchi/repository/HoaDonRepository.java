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
    @Query("UPDATE HoaDon h SET h.status = :status WHERE h.hoadon_id = :id")
    int updateStatusById(@Param("id") Long id, @Param("status") int status);

    @Modifying
    @Transactional
    @Query("UPDATE HoaDon h SET h.status = :status where h.orderCode = :orderCode")
    int updateStatusByOrderCode(@Param("orderCode") String orderCode, @Param("status") int status);

    @Transactional
    @Query("SELECT h.hopDong.hopdong_id FROM HoaDon h WHERE h.orderCode = :orderCode")
    Long findHopDongByOrderCode(@Param("orderCode") String orderCode);

    @Transactional
    @Query("SELECT h FROM HoaDon h WHERE h.hoadon_id = :id")
    HoaDon findHoaDonByHoadon_id(Long id);

    HoaDon findHoaDonByOrderCode(String orderCode);
}
