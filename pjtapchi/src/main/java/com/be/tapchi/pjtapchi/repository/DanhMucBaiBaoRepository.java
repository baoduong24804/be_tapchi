package com.be.tapchi.pjtapchi.repository;

import com.be.tapchi.pjtapchi.model.Danhmucbaibao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DanhMucBaiBaoRepository extends JpaRepository<Danhmucbaibao, Long> {
    List<Danhmucbaibao> findByDanhMucId(Long danhmucId);
}
