package com.be.tapchi.pjtapchi.service;

import com.be.tapchi.pjtapchi.model.Baibao;
import com.be.tapchi.pjtapchi.model.DanhMuc;
import com.be.tapchi.pjtapchi.model.Danhmucbaibao;
import com.be.tapchi.pjtapchi.repository.BaiBaoRepository;
import com.be.tapchi.pjtapchi.repository.DanhMucBaiBaoRepository;
import com.be.tapchi.pjtapchi.repository.DanhMucRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DanhMucService {
    @Autowired
    private DanhMucRepository danhMucRepository;
    @Autowired
    private DanhMucBaiBaoRepository danhMucBaiBaoRepository;
    @Autowired
    private BaiBaoRepository baiBaoRepository;

//    public List<DanhMuc> getAllDanhMuc() {
//        return danhMucRepository.findAll();
//    }

    public Page<DanhMuc> getAllDanhMuc(Pageable pageable) {
        return danhMucRepository.findAll(pageable);
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

    public Page<DanhMuc> findAll(Pageable pageable) {
        return danhMucRepository.findAll(pageable);
    }

    public List<Baibao> getBbByIdDanhMuc(Long id) {
        List<Danhmucbaibao> danhmucbb = danhMucBaiBaoRepository.findByDanhMucId(id);
        List<Baibao> bB = new ArrayList<>();
        for (Danhmucbaibao danhmucbaibao : danhmucbb) {
            baiBaoRepository.findById(danhmucbaibao.getId()).ifPresent(bB::add);
        }
        return bB;
    }
}