package com.be.tapchi.pjtapchi.service;

import com.be.tapchi.pjtapchi.model.Baibao;
import com.be.tapchi.pjtapchi.model.Taikhoan;
import com.be.tapchi.pjtapchi.model.Theloai;
import com.be.tapchi.pjtapchi.repository.BaiBaoRepository;
import com.be.tapchi.pjtapchi.repository.TaiKhoanRepository;
import com.be.tapchi.pjtapchi.repository.TheloaiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class BaibaoService {
    @Autowired
    private final BaiBaoRepository baiBaoRepository;
    @Autowired
    private final TheloaiRepository theloaiRepository;
    @Autowired
    private final TaiKhoanRepository taiKhoanRepository;

    
    public BaibaoService(TaiKhoanRepository taiKhoanRepository, BaiBaoRepository baiBaoRepository, TheloaiRepository theloaiRepository) {
        this.taiKhoanRepository = taiKhoanRepository;
        this.baiBaoRepository = baiBaoRepository;
        this.theloaiRepository = theloaiRepository;
    }

    public Page<Baibao> findAllBaibao(Pageable pageable) {
        return baiBaoRepository.findAll(pageable);
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

    public Page<Baibao> getBaibaoByTacGiaId(Long id, Pageable pageable) {
        Taikhoan taikhoan = taiKhoanRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid Taikhoan ID"));
        return baiBaoRepository.findBaibaoByTaikhoan(taikhoan, pageable);
    }

    public Page<Baibao> getBaibaoByTheLoaiID(Integer id, Pageable pageable) {
        Theloai theloai = theloaiRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid Theloai ID"));
        return baiBaoRepository.findBaibaoByTheloai(theloai, pageable);
    }

    public Page<Baibao> getBaibaoByTieuDe(String tieude, Pageable pageable) {
        return baiBaoRepository.findBaibaoByTieudeContaining(tieude, pageable);
    }

    public Page<Baibao> getBaibaoByNgayDang(LocalDate postDate, Pageable pageable) {
        return baiBaoRepository.findBaibaoByNgaydang(postDate, pageable);
    }

    public Page<Baibao> getBaibaiByNgayDangBetween(LocalDate date1, LocalDate date2, Pageable pageable) {
        return baiBaoRepository.findByNgaydangBetween(date1, date2, pageable);
    }

    public Page<Baibao> getBaibaoByTrangThai(Integer status, Pageable pageable) {
        return baiBaoRepository.findBaibaoByStatus(status, pageable);
    }

}