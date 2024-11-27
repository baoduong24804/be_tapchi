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

    public void deleteTaiKhoanById(Long id) {
        taiKhoanRepository.deleteById(id);
    }

    public boolean deleteTaiKhoan(Taikhoan entity) {
        try {
            taiKhoanRepository.delete(entity);
            return true;
        } catch (Exception e) {
            // TODO: handle exception
        }
        return false;
    }

    public boolean checkTaikhoan(LoginRequest user) {
        // trong username
        try {
            
            if (user.getUsername() == null ||  user.getPassword() == null) {
                return false;
            }
            System.out.println("Tim tk: "+user.getUsername());
            Taikhoan tk = findByUsername(user.getUsername().trim());
            
            Taikhoan tk_e = taiKhoanRepository.findByEmailAndGoogleIdIsNull(user.getUsername().trim());
            
            Taikhoan tk_sdt = findBySdt(user.getUsername().trim());
            if(tk == null && tk_e == null && tk_sdt == null){
                return false;
            }

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return false;
        }

        return true;

    }

    public Taikhoan getTaikhoanLogin(LoginRequest entity) {

        try {
            Taikhoan username = taiKhoanRepository.findByUsername(entity.getUsername()+"".trim());
            if (username != null) {
                return username;
            }
            Taikhoan email = taiKhoanRepository.findByEmailAndGoogleIdIsNull(entity.getUsername()+"".trim());
            if (email != null) {
                return email;
            }

            Taikhoan sdt = taiKhoanRepository.findBySdt(entity.getUsername()+"".trim());
            if (sdt != null) {
                return sdt;
            }

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return null;
    }

    public Taikhoan getTaikhoanLogin(String username) {
        if(username == null){
            return null;
        }
        try {
            Taikhoan tkusername = taiKhoanRepository.findByUsername(username.trim());
            if (tkusername != null) {
                return tkusername;
            }
            Taikhoan email = taiKhoanRepository.findByEmailAndGoogleIdIsNull(username.trim());
            if (email != null) {
                return email;
            }

            Taikhoan sdt = taiKhoanRepository.findBySdt(username.trim());
            if (sdt != null) {
                return sdt;
            }

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return null;
    }

    public boolean checkUserLockedorNotActice(Taikhoan tk){
        try {
            if(tk.getStatus() == -1 || tk.getStatus() == 0){
                return true;
            }
        } catch (Exception e) {
            // TODO: handle exception
            return true;
        }
        return false;
    }

    public boolean loginTaikhoan(LoginRequest user) {

        if (user.getUsername().isBlank() || user.getUsername().isEmpty()) {
            return false;
        }
        Taikhoan tk = getTaikhoanLogin(user);// lay tk tu username
       

        if (tk != null) {

            if (passwordEncoder.matches(user.getPassword().trim(), tk.getPassword().trim())) {
                return true;
            }

            return false;
        }

        return false;

    }

    // neu tk va mk dung
    public boolean loginTaikhoan(ChangePassword user) {

        try {
            if (user.getUsername().isBlank() || user.getUsername().isEmpty()) {
                return false;
            }
            Taikhoan tk = findByUsername(user.getUsername().trim());
            Taikhoan tk_e = findByEmail(user.getUsername().trim());
            if (tk != null) {

                if (passwordEncoder.matches(user.getPassword().trim(), tk.getPassword().trim())) {
                    return true;
                }

                return false;
            }

            if (tk_e != null) {

                if (passwordEncoder.matches(user.getPassword().trim(), tk_e.getPassword().trim())) {
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

    public Taikhoan findBySdt(String sdt){
        return taiKhoanRepository.findBySdt(sdt);
    }

    public Taikhoan findByUsername(String username) {
        return taiKhoanRepository.findByUsername(username);
    }

    public Taikhoan findByEmail(String email) {
        Taikhoan tk = taiKhoanRepository.findByEmailAndGoogleIdIsNull(email);
        if (tk == null) {
            return null;
        }

        return tk;

    }

    public boolean existsByUsername(String username) {
        return taiKhoanRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        if (taiKhoanRepository.existsByEmailAndGoogleIdIsNull(email)) {
            return true;
        }
        return false;
    }

    public boolean existsBySdt(String sdt) {
        if (taiKhoanRepository.existsBySdt(sdt)) {
            return true;
        }
        return false;
    }

    // @Transactional
    // public void saveTaiKhoanAndChiTiet(Taikhoan taiKhoan) {
    // // Lưu thông tin TaiKhoan
    // Taikhoan savedTaiKhoan = taiKhoanRepository.save(taiKhoan);
    // }
}
