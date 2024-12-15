package com.be.tapchi.pjtapchi.repository;

import com.be.tapchi.pjtapchi.model.HoaDon;
import com.be.tapchi.pjtapchi.model.HopDong;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface HopDongRepository extends JpaRepository<HopDong, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE HopDong h SET h.status = :status WHERE h.hopdong_id = :id")
    int updateStatusById(@Param("id") Long id, @Param("status") int status);

    @Modifying
    @Transactional
    @Query("UPDATE HopDong h SET h.status = :status WHERE h.hopdong_id = :id")
    int updateStatusAndQCById(@Param("id") Long id, @Param("status") int status);


    @Modifying
    @Transactional
    @Query("UPDATE HopDong h set h.status = :status, h.hoaDon = :hoaDon WHERE h.hopdong_id = :id")
    int updateStatusAndHoaDonById(@Param("id") Long id, @Param("status") int status, @Param("hoaDon") HoaDon hoaDon);

    // Update HopDong status = 1, HoaDon, quangcao_id by id
    @Modifying
    @Transactional
    @Query("UPDATE HopDong h set h.status = :status, h.hoaDon = :hoaDon, h.QuangCao = :quangcao_id WHERE h.hopdong_id = :id")
    int updateStatusAndHoaDonAndQuangCaoById(@Param("id") Long id, @Param("status") int status,
                                             @Param("hoaDon") Long hoaDon, @Param("quangcao_id") Long quangcao_id);

    @Query("SELECT hd FROM HopDong hd JOIN hd.hoaDon h WHERE h.status = :status")
    List<HopDong> findHopDongsWithHoaDonStatus(@Param("status") int status);

    Page<HopDong> findByStatus(int status, Pageable pageable);

}