package com.be.tapchi.pjtapchi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.be.tapchi.pjtapchi.model.Kiemduyet;

//Extending JpaRepository interface for Kiemduyet entity to perform CRUD operations.
public interface KiemduyetRepository extends JpaRepository<Kiemduyet, Long> {
}
