package com.be.tapchi.pjtapchi.repository;

import com.be.tapchi.pjtapchi.model.Baibao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//Extending JpaRepository interface for Baibao entity to perform CRUD operations.
public interface BaiBaoRepository extends JpaRepository<Baibao, Long> {


//    List<Baibao> findByTieuDeContaining(String tieuDe);
//
//    List<Baibao> findByNoiDungContaining(String noiDung);
//
//    List<Baibao> findByNgayDang(String ngayDang);
//
//    List<Baibao> findByTrangThai(String trangThai);
//
//    List<Baibao> findByTacGia(String tacGia);
//
//    List<Baibao> findByTheLoai(String theLoai);
//
//    List<Baibao> findByTheLoaiID(Long theLoaiID);
//
//    List<Baibao> findByTacGiaID(Long tacGiaID);
//
//    List<Baibao> findBySoLuotXem(int soLuotXem);
//
//    List<Baibao> findBySoLuotThich(int soLuotThich);
//
//    List<Baibao> findBySoLuotBinhLuan(int soLuotBinhLuan);
//
//    List<Baibao> findByKiemDuyet(Boolean kiemDuyet);
}