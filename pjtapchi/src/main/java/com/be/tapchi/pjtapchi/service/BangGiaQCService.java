package com.be.tapchi.pjtapchi.service;

import com.be.tapchi.pjtapchi.model.BangGiaQC;
import com.be.tapchi.pjtapchi.repository.BangGiaQCRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BangGiaQCService {
    @Autowired
    private final BangGiaQCRepository bangGiaQCRepository;

    public BangGiaQCService(BangGiaQCRepository bangGiaQCRepository) {
        this.bangGiaQCRepository = bangGiaQCRepository;
    }

    public List<BangGiaQC> findAll() {
        return bangGiaQCRepository.findAll();
    }

    public BangGiaQC findById(Long id) {
        return bangGiaQCRepository.findBangGiaQCByBangGiaQCId(id);
    }

    public BangGiaQC save(BangGiaQC bangGiaQC) {
        return bangGiaQCRepository.save(bangGiaQC);
    }

    public void deleteById(Long id) {
        bangGiaQCRepository.deleteById(id);
    }

    public Integer findSoNgayByID(Long id) {
        return bangGiaQCRepository.findSoNgayByID(id);
    }
    // Thêm các phương thức khác nếu cần
}