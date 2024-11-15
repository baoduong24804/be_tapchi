package com.be.tapchi.pjtapchi.repository;

import com.be.tapchi.pjtapchi.model.Danhmucbaibao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DanhMucBaiBaoRepository extends JpaRepository<Danhmucbaibao, Long> {
    List<Danhmucbaibao> findByDanhMucId(Long danhmucId);
}
