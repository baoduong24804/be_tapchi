package com.be.tapchi.pjtapchi.service;

import com.be.tapchi.pjtapchi.model.HopDong;
import com.be.tapchi.pjtapchi.repository.HopDongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class HopDongService {

    @Autowired
    private HopDongRepository hopDongRepository;

    public List<HopDong> getAllHopDongs() {
        return hopDongRepository.findAll();
    }

    public HopDong getHopDongById(Long id) {
        return hopDongRepository.findById(id).orElse(null);
    }

    public HopDong saveHopDong(HopDong hopDong) {
        return hopDongRepository.save(hopDong);
    }

    public void deleteHopDong(Long id) {
        hopDongRepository.deleteById(id);
    }
}
