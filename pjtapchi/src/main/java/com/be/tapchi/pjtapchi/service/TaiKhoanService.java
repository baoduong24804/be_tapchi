package com.be.tapchi.pjtapchi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.be.tapchi.pjtapchi.controller.user.model.LoginRequest;
import com.be.tapchi.pjtapchi.model.Taikhoan;
import com.be.tapchi.pjtapchi.model.Taikhoanchitiet;
import com.be.tapchi.pjtapchi.repository.TaiKhoanRepository;
import com.be.tapchi.pjtapchi.repository.TaiKhoanchitietRepository;

@Service
public class TaiKhoanService {
    @Autowired
    private TaiKhoanRepository taiKhoanRepository;

    @Autowired
    private TaiKhoanchitietRepository taiKhoanchitietRepository;

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

    public boolean loginTaikhoan(LoginRequest user){
        // trong email
        if(user.getUsername().isBlank() || user.getUsername().isEmpty()){
            return false;
        }
        Taikhoan tk = findByUsername(user.getUsername().trim());
        Taikhoan tk_e = findByEmail(user.getUsername().trim());
        if(tk == null && tk_e == null){
            return false;
        }

        return true;

    }

    public Taikhoan findByUsername(String username){
        return taiKhoanRepository.findByUsername(username);
    }

    

    public Taikhoan findByEmail(String email){
        Taikhoanchitiet tkct = taiKhoanchitietRepository.findByEmail(email);
        if(tkct == null){
            return null;
        }
        Taikhoan tk = taiKhoanRepository.findById(tkct.getTaikhoan_id()).orElse(null);

        return tk;

    }

    public boolean existsByUsername(String username){
        return taiKhoanRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email){
        if(taiKhoanchitietRepository.existsByEmail(email)){
            return true;
        }
        return false;
    }

    public boolean existsBySdt(String sdt){
        if(taiKhoanchitietRepository.existsBySdt(sdt)){
            return true;
        }
        return false;
    }

    @Transactional
    public void saveTaiKhoanAndChiTiet(Taikhoan taiKhoan, Taikhoanchitiet taiKhoanChiTiet) {
        // Lưu thông tin TaiKhoan
        Taikhoan savedTaiKhoan = taiKhoanRepository.save(taiKhoan);

        // Set TaiKhoan cho TaiKhoanChiTiet và lưu TaiKhoanChiTiet
        taiKhoanChiTiet.setTaikhoan(savedTaiKhoan);
        taiKhoanchitietRepository.save(taiKhoanChiTiet);
    }
}
