package com.be.tapchi.pjtapchi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.be.tapchi.pjtapchi.model.Vaitro;

@Repository
public interface VaiTroRepository extends JpaRepository<Vaitro, Integer> {
    Vaitro findBytenrole(String name);
}
