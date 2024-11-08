package com.be.tapchi.pjtapchi.service;

import com.be.tapchi.pjtapchi.model.DanhMuc;
import com.be.tapchi.pjtapchi.repository.DanhMucRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DanhMucService {
    @Autowired
    private DanhMucRepository danhMucRepository;

    public List<DanhMuc> getAllDanhMuc() {
        return danhMucRepository.findAll();
    }

    public DanhMuc getDanhMucById(Long id) {
        return danhMucRepository.findById(id).orElse(null);
    }

    public DanhMuc saveDanhMuc(DanhMuc danhMuc) {
        return danhMucRepository.save(danhMuc);
    }

    public void deleteDanhMuc(Long id) {
        danhMucRepository.deleteById(id);
    }

    public DanhMuc updateDanhMuc(Long id, DanhMuc newDanhMuc) {
        return danhMucRepository.findById(id).map(danhMuc -> {
            danhMuc.setTieuDe(newDanhMuc.getTieuDe());
            danhMuc.setMoTa(newDanhMuc.getMoTa());
            return danhMucRepository.save(danhMuc);
        }).orElse(null);
    }

}