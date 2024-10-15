package com.be.tapchi.pjtapchi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.be.tapchi.pjtapchi.model.Binhluan;

//Extending JpaRepository interface for Binhluan entity to perform CRUD operations.
public interface BinhluanRepository extends JpaRepository<Binhluan,Long>{
}
