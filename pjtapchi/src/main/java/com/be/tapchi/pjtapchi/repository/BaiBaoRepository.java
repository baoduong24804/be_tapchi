package com.be.tapchi.pjtapchi.repository;

import com.be.tapchi.pjtapchi.model.Baibao;
import com.be.tapchi.pjtapchi.model.Taikhoan;
import com.be.tapchi.pjtapchi.model.Theloai;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

//Extending JpaRepository interface for Baibao entity to perform CRUD operations.
public interface BaiBaoRepository extends JpaRepository<Baibao, Integer> {

    Baibao findBaibaoById(Integer id);

    List<Baibao> findBaibaoByNgaydang(LocalDate ngaydang);

    List<Baibao> findByNgaydangBetween(LocalDate date1, LocalDate date2);

    List<Baibao> findBaibaoByTaikhoan(Taikhoan taikhoan);

    List<Baibao> findBaibaoByTheloai(Theloai theloai);

    List<Baibao> findBaibaoByTieudeContaining(String tieude);

    List<Baibao> findBaibaoByNoidungContaining(String noidung);

    List<Baibao> findBaibaoByStatus(Integer status);

    List<Baibao> findBaibaoByNgaydangAndStatus(LocalDate ngaydang, Integer status);

    List<Baibao> findBaibaoByNgaydangBetweenAndStatus(LocalDate date1, LocalDate date2, Integer status);


    List<Baibao> findBaibaoByTieudeContainingAndStatus(String tieude, Integer status);

    List<Baibao> findBaibaoByNoidungContainingAndStatus(String noidung, Integer status);


}
