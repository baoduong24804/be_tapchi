package com.be.tapchi.pjtapchi.repository;

import com.be.tapchi.pjtapchi.model.QuangCao;
import com.be.tapchi.pjtapchi.model.Taikhoan;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface QuangCaoRepository extends JpaRepository<QuangCao, Long> {
    List<QuangCao> findByTaikhoan(Taikhoan taikhoan);
}
