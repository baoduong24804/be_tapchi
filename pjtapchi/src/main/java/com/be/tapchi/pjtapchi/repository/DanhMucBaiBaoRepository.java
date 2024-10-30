package com.be.tapchi.pjtapchi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.be.tapchi.pjtapchi.model.DanhMuc;
import com.be.tapchi.pjtapchi.model.Danhmucbaibao;

public interface DanhMucBaiBaoRepository extends JpaRepository<Danhmucbaibao, Long> {
}
