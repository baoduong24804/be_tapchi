package com.be.tapchi.pjtapchi.repository;

import com.be.tapchi.pjtapchi.model.Thich;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ThichRepository extends JpaRepository<Thich, Long> {

    @Query("SELECT t FROM Thich t WHERE t.baibao.id = :baibaoid AND t.taikhoan.taikhoan_id = :taikhoanid")
    Optional<Thich> findByBaibaoidAndTaikhoanid(@Param("baibaoid") Long baibaoid, @Param("taikhoanid") Long taikhoanid);
}
