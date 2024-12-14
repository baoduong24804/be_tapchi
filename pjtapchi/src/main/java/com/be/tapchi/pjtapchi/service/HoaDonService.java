package com.be.tapchi.pjtapchi.service;

import com.be.tapchi.pjtapchi.model.HoaDon;
import com.be.tapchi.pjtapchi.repository.HoaDonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HoaDonService {
    @Autowired
    private final HoaDonRepository hoaDonRepository;


    public HoaDonService(HoaDonRepository hoaDonRepository) {
        this.hoaDonRepository = hoaDonRepository;
    }

    public List<HoaDon> findAll() {
        return hoaDonRepository.findAll();
    }

    public HoaDon findById(Long id) {
        return hoaDonRepository.findHoaDonByHoadon_id(id);
    }

    public HoaDon save(HoaDon hoaDon) {
        return hoaDonRepository.save(hoaDon);
    }

    public int updateStatusById(Long id, int status) {
        return hoaDonRepository.updateStatusById(id, status);
    }

    public HoaDon findHoaDonByOrderCode(String orderCode) {
        return hoaDonRepository.findHoaDonByOrderCode(orderCode);
    }

    public int updateStatusByOrderCode(String orderCode, int status) {
        return hoaDonRepository.updateStatusByOrderCode(orderCode, status);
    }

    public Long findHopDongByOrderCode(String orderCode) {
        return hoaDonRepository.findHopDongByOrderCode(orderCode);
    }


    public void deleteById(Long id) {
        hoaDonRepository.deleteById(id);
    }

    public List<HoaDon> findHoaDonByTaikhoanTaikhoan_id(Long id) {
        return hoaDonRepository.findHoaDonByTaikhoanTaikhoan_id(id);
    }

    // Thêm các phương thức khác nếu cần
}
