package com.be.tapchi.pjtapchi.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import com.be.tapchi.pjtapchi.model.Theloai;

//Extending JpaRepository interface for Theloai entity to perform CRUD operations.

public interface TheloaiRepository extends JpaRepository<Theloai,Long>{

}
