package com.be.tapchi.pjtapchi.repository;

import com.be.tapchi.pjtapchi.model.QuangCao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuangCaoRepository extends JpaRepository<QuangCao, Long> {
}
