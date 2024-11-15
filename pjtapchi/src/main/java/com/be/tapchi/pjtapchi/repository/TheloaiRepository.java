package com.be.tapchi.pjtapchi.repository;

import com.be.tapchi.pjtapchi.model.Theloai;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//Extending JpaRepository interface for Theloai entity to perform CRUD operations.
@Repository
public interface TheloaiRepository extends JpaRepository<Theloai, Integer> {


    Theloai findTheloaiById(Integer id);

    void deleteTheloaiByTenloai(String tenloai);
}
