package com.be.tapchi.pjtapchi.service;

import com.be.tapchi.pjtapchi.model.Baibao;
import com.be.tapchi.pjtapchi.model.Taikhoan;
import com.be.tapchi.pjtapchi.model.Theloai;
import com.be.tapchi.pjtapchi.repository.BaiBaoRepository;
import com.be.tapchi.pjtapchi.repository.TaiKhoanRepository;
import com.be.tapchi.pjtapchi.repository.TheloaiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BaibaoService {

    private final BaiBaoRepository baiBaoRepository;
    private final TheloaiRepository theloaiRepository;
    private final TaiKhoanRepository taiKhoanRepository;

    @Autowired
    public BaibaoService(TaiKhoanRepository taiKhoanRepository, BaiBaoRepository baiBaoRepository, TheloaiRepository theloaiRepository) {
        this.taiKhoanRepository = taiKhoanRepository;
        this.baiBaoRepository = baiBaoRepository;
        this.theloaiRepository = theloaiRepository;
    }

    public List<Baibao> findAllBaibao() {
        return baiBaoRepository.findAll();
    }

    public Baibao getBaibaoById(Integer id) {
        return baiBaoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid Baibao ID"));
    }

    public Baibao saveBaibao(Baibao baibao) {
        return baiBaoRepository.save(baibao);
    }

    public void deleteBaibao(Integer id) {
        baiBaoRepository.deleteById(id);
    }

    public List<Baibao> getBaibaoByTacGiaId(Long id) {
        Taikhoan taikhoan = taiKhoanRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid Taikhoan ID"));
        return baiBaoRepository.findBaibaoByTaikhoan(taikhoan);
    }

    public List<Baibao> getBaibaoByTheLoaiID(Integer id) {
        Theloai theloai = theloaiRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid Theloai ID"));
        return baiBaoRepository.findBaibaoByTheloai(theloai);
    }

    public List<Baibao> getBaibaoByTieuDe(String tieude) {
        return baiBaoRepository.findBaibaoByTieudeContaining(tieude);
    }

    public List<Baibao> getBaibaoByNgayDang(LocalDate postDate) {
        return baiBaoRepository.findBaibaoByNgaydang(postDate);
    }

    public List<Baibao> getBaibaiByNgayDangBetween(LocalDate date1, LocalDate date2) {
        return baiBaoRepository.findByNgaydangBetween(date1, date2);
    }

    public List<Baibao> getBaibaoByTrangThai(Integer status) {
        return baiBaoRepository.findBaibaoByStatus(status);
    }
}