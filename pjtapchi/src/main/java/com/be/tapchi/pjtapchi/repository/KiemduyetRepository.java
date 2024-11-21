package com.be.tapchi.pjtapchi.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.be.tapchi.pjtapchi.model.Baibao;
import com.be.tapchi.pjtapchi.model.Kiemduyet;
import com.be.tapchi.pjtapchi.model.Taikhoan;

//Extending JpaRepository interface for Kiemduyet entity to perform CRUD operations.
@Repository
public interface KiemduyetRepository extends JpaRepository<Kiemduyet, Long> {
    List<Kiemduyet> findByTaikhoan(Taikhoan taikhoan);
    Page<Kiemduyet> findByStatus(Integer status, Pageable pageable);
    List<Kiemduyet> findByBaibao(Baibao baibao);

}
