package com.be.tapchi.pjtapchi.repository;

import com.be.tapchi.pjtapchi.model.Thich;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThichRepository extends JpaRepository<Thich, Long> {


}
