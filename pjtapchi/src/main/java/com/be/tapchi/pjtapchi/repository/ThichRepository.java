package com.be.tapchi.pjtapchi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.be.tapchi.pjtapchi.model.Thich;

@Repository
public interface ThichRepository extends JpaRepository<Thich, Long> {
}
