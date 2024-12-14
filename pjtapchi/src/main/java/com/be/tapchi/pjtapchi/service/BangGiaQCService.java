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

    public BangGiaQC findBangGiaQCByBanggiaqc_id(Long ids) {
        return bangGiaQCRepository.findBybanggiaqc_id(ids);
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

    public BangGiaQC findBybanggiaqc_id(Long id) {
        return bangGiaQCRepository.findBybanggiaqc_id(id);
    }
}