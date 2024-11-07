package com.be.tapchi.pjtapchi.service;

import com.be.tapchi.pjtapchi.model.DanhMuc;
import com.be.tapchi.pjtapchi.repository.DanhMucRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DanhMucService {
    @Autowired
    private static DanhMucRepository danhMucRepository;

    public static List<DanhMuc> getAllDanhMuc() {
        return danhMucRepository.findAll();
    }

}