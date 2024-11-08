package com.be.tapchi.pjtapchi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.be.tapchi.pjtapchi.controller.user.model.ChangePassword;
import com.be.tapchi.pjtapchi.controller.user.model.LoginRequest;
import com.be.tapchi.pjtapchi.model.Taikhoan;
import com.be.tapchi.pjtapchi.repository.TaiKhoanRepository;


@Service
public class TaiKhoanService {
    @Autowired
    private TaiKhoanRepository taiKhoanRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

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

    public boolean checkTaikhoan(LoginRequest user){
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

    public boolean loginTaikhoan(LoginRequest user){
     
        if(user.getUsername().isBlank() || user.getUsername().isEmpty()){
            return false;
        }
        Taikhoan tk = findByUsername(user.getUsername().trim());
        Taikhoan tk_e = findByEmail(user.getUsername().trim());
        if(tk != null){
            
            if(passwordEncoder.matches(user.getPassword(), tk.getPassword())){
                return true;
            }

            return false;
        }

        if(tk_e != null){
            
            if(passwordEncoder.matches(user.getPassword(), tk_e.getPassword())){
                return true;
            }
            
            return false;
        }

        return false;

    }

    // neu tk va mk dung
    public boolean loginTaikhoan(ChangePassword user){
        
        try {
            if(user.getUsername().isBlank() || user.getUsername().isEmpty()){
                return false;
            }
            Taikhoan tk = findByUsername(user.getUsername().trim());
            Taikhoan tk_e = findByEmail(user.getUsername().trim());
            if(tk != null){
                
                if(passwordEncoder.matches(user.getPassword().trim(), tk.getPassword().trim())){
                    return true;
                }
    
                return false;
            }
    
            if(tk_e != null){
                
                if(passwordEncoder.matches(user.getPassword().trim(), tk_e.getPassword().trim())){
                    return true;
                }
                
                return false;
            }
    
            
        } catch (Exception e) {
            // TODO: handle exception
            throw e;
        }
        return false;

    }
    

    public Taikhoan findByUsername(String username){
        return taiKhoanRepository.findByUsername(username);
    }

    

    public Taikhoan findByEmail(String email){
        Taikhoan tk = taiKhoanRepository.findByEmail(email);
        if(tk == null){
            return null;
        }
        

        return tk;

    }

    public boolean existsByUsername(String username){
        return taiKhoanRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email){
        if(taiKhoanRepository.existsByEmail(email)){
            return true;
        }
        return false;
    }

    public boolean existsBySdt(String sdt){
        if(taiKhoanRepository.existsBySdt(sdt)){
            return true;
        }
        return false;
    }

    @Transactional
    public void saveTaiKhoanAndChiTiet(Taikhoan taiKhoan) {
        // Lưu thông tin TaiKhoan
        Taikhoan savedTaiKhoan = taiKhoanRepository.save(taiKhoan);
    }
}
