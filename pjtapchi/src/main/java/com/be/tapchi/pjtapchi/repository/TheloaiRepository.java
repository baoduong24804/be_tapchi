package com.be.tapchi.pjtapchi.repository;

import com.be.tapchi.pjtapchi.model.Theloai;
import org.springframework.data.jpa.repository.JpaRepository;

//Extending JpaRepository interface for Theloai entity to perform CRUD operations.

public interface TheloaiRepository extends JpaRepository<Theloai, Integer> {


    Theloai findTheloaiById(Integer id);
}
