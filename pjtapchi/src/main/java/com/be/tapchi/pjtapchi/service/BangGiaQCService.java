package com.be.tapchi.pjtapchi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.be.tapchi.pjtapchi.model.BangGiaQC;
import com.be.tapchi.pjtapchi.repository.BangGiaQCRepository;

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

    public Optional<BangGiaQC> findById(Long id) {
        return bangGiaQCRepository.findById(id);
    }

    public BangGiaQC save(BangGiaQC bangGiaQC) {
        return bangGiaQCRepository.save(bangGiaQC);
    }

    public void deleteById(Long id) {
        bangGiaQCRepository.deleteById(id);
    }

    // Thêm các phương thức khác nếu cần
}