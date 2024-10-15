package com.be.tapchi.pjtapchi.repository;

import com.be.tapchi.pjtapchi.model.Baibao;
import org.springframework.data.jpa.repository.JpaRepository;

//Extending JpaRepository interface for Baibao entity to perform CRUD operations.
public interface BaiBaoRepository  extends JpaRepository<Baibao,Long> {}