package com.be.tapchi.pjtapchi.repository;

import com.be.tapchi.pjtapchi.model.Baibao;
import com.be.tapchi.pjtapchi.model.Taikhoan;
import com.be.tapchi.pjtapchi.model.Theloai;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

//Extending JpaRepository interface for Baibao entity to perform CRUD operations.
public interface BaiBaoRepository extends JpaRepository<Baibao, Integer> {

    Baibao findBaibaoById(Integer id);

    Page<Baibao> findBaibaoByNgaydang(LocalDate ngaydang, Pageable pageable);

    Page<Baibao> findByNgaydangBetween(LocalDate date1, LocalDate date2, Pageable pageable);

    Page<Baibao> findBaibaoByTaikhoan(Taikhoan taikhoan, Pageable pageable);

    Page<Baibao> findBaibaoByTheloai(Theloai theloai, Pageable pageable);

    Page<Baibao> findBaibaoByTieudeContaining(String tieude, Pageable pageable);

    Page<Baibao> findBaibaoByNoidungContaining(String noidung, Pageable pageable);

    Page<Baibao> findBaibaoByStatus(Integer status, Pageable pageable);

    Page<Baibao> findBaibaoByNgaydangAndStatus(LocalDate ngaydang, Integer status, Pageable pageable);

    Page<Baibao> findBaibaoByNgaydangBetweenAndStatus(LocalDate date1, LocalDate date2, Integer status, Pageable pageable);


    Page<Baibao> findBaibaoByTieudeContainingAndStatus(String tieude, Integer status, Pageable pageable);

    Page<Baibao> findBaibaoByNoidungContainingAndStatus(String noidung, Integer status, Pageable pageable);


}
