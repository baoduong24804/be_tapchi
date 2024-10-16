package com.be.tapchi.pjtapchi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.be.tapchi.pjtapchi.model.HoaDon;
import com.be.tapchi.pjtapchi.repository.HoaDonRepository;

@Service
public class HoaDonService {

    private final HoaDonRepository hoaDonRepository;

    @Autowired
    public HoaDonService(HoaDonRepository hoaDonRepository) {
        this.hoaDonRepository = hoaDonRepository;
    }

    public List<HoaDon> findAll() {
        return hoaDonRepository.findAll();
    }

    public Optional<HoaDon> findById(Long id) {
        return hoaDonRepository.findById(id);
    }

    public HoaDon save(HoaDon hoaDon) {
        return hoaDonRepository.save(hoaDon);
    }

    public void deleteById(Long id) {
        hoaDonRepository.deleteById(id);
    }

    // Thêm các phương thức khác nếu cần
}
