package com.be.tapchi.pjtapchi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.be.tapchi.pjtapchi.model.Taikhoan;
import com.be.tapchi.pjtapchi.repository.TaiKhoanRepository;

@Service
public class TaiKhoanService {
    @Autowired
    private TaiKhoanRepository taiKhoanRepository;

    public List<Taikhoan> getAllTaiKhoans() {
        return taiKhoanRepository.findAll();
    }

    public Optional<Taikhoan> getTaiKhoanById(Long id) {
        return taiKhoanRepository.findById(id);
    }

    public Taikhoan saveTaiKhoan(Taikhoan taiKhoan) {
        return taiKhoanRepository.save(taiKhoan);
    }

    public void deleteTaiKhoan(Long id) {
        taiKhoanRepository.deleteById(id);
    }
    public Taikhoan findByUsername(String username){
        return taiKhoanRepository.findByUsername(username);
    }

    public boolean existsByUsername(String username){
        return taiKhoanRepository.existsByUsername(username);
    }
}
