package com.be.tapchi.pjtapchi.repository;

import com.be.tapchi.pjtapchi.model.Binhluan;
import com.be.tapchi.pjtapchi.model.Thich;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

//Extending JpaRepository interface for Binhluan entity to perform CRUD operations.
@Repository
public interface BinhluanRepository extends JpaRepository<Binhluan, Long> {
    // List<Binhluan> findByBaibao_Id(Long id);
    @Query("SELECT t FROM Binhluan t WHERE t.baibao.id = :baibaoid AND t.taikhoan.taikhoan_id = :taikhoanid")
    Optional<Binhluan> findByBaibaoidAndTaikhoanid(@Param("baibaoid") Long baibaoid, @Param("taikhoanid") Long taikhoanid);
}
