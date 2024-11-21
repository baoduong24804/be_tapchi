package com.be.tapchi.pjtapchi.service;

import com.be.tapchi.pjtapchi.controller.danhmuc.utils.DateUtils;
import com.be.tapchi.pjtapchi.model.DanhMuc;
import com.be.tapchi.pjtapchi.repository.BaiBaoRepository;
import com.be.tapchi.pjtapchi.repository.DanhMucBaiBaoRepository;
import com.be.tapchi.pjtapchi.repository.DanhMucRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class DanhMucService {

    @Autowired
    private DanhMucRepository danhMucRepository;
    @Autowired
    private DanhMucBaiBaoRepository danhMucBaiBaoRepository;
    @Autowired
    private BaiBaoRepository baiBaoRepository;

    // public List<DanhMuc> getAllDanhMuc() {
    // return danhMucRepository.findAll();
    // }

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

    public Page<DanhMuc> getDanhmucInCurrentWeek(int page, int size, int status) {
        LocalDate startOfWeek = DateUtils.getStartOfWeek();
        LocalDate endOfWeek = DateUtils.getEndOfWeek();
        System.out.println(startOfWeek + "," + endOfWeek);
        Pageable pageable = PageRequest.of(page, size);

        return danhMucRepository.findDanhmucByWeekAndStatus(startOfWeek, endOfWeek, status, pageable);
    }

    public DanhMuc updateDanhMuc(Long id, DanhMuc newDanhMuc) {
        return danhMucRepository.findById(id).map(danhMuc -> {
            danhMuc.setTieude(newDanhMuc.getTieude());
            danhMuc.setMota(newDanhMuc.getMota());
            return danhMucRepository.save(danhMuc);
        }).orElse(null);
    }

    public Page<DanhMuc> findAll(Pageable pageable) {
        return danhMucRepository.findAll(pageable);
    }

//    public List<Baibao> getBbByIdDanhMuc(Long id) {
//        List<Danhmucbaibao> danhmucbaibaos
//
//        List<Baibao> baibaos = new ArrayList<>();
//        for (Danhmucbaibao danhmucbaibao : danhmucbaibaos) {
//            baibaos.add(danhmucbaibao.getBaibao());
//        }
//        return baibaos;
//    }


}