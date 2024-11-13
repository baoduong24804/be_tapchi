package com.be.tapchi.pjtapchi.repository;

import com.be.tapchi.pjtapchi.model.Binhluan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//Extending JpaRepository interface for Binhluan entity to perform CRUD operations.
@Repository
public interface BinhluanRepository extends JpaRepository<Binhluan, Long> {
    //List<Binhluan> findByBaibao_Id(Long id);

}
