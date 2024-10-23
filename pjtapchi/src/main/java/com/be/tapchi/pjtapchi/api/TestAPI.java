package com.be.tapchi.pjtapchi.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.be.tapchi.pjtapchi.model.Taikhoan;
import com.be.tapchi.pjtapchi.service.EmailService;
import com.be.tapchi.pjtapchi.service.TaiKhoanService;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.be.tapchi.pjtapchi.model.BangGiaQC;
import com.be.tapchi.pjtapchi.model.HoaDon;
import com.be.tapchi.pjtapchi.model.HopDong;
import com.be.tapchi.pjtapchi.model.Taikhoan;
import com.be.tapchi.pjtapchi.service.BangGiaQCService;
import com.be.tapchi.pjtapchi.service.HoaDonService;
import com.be.tapchi.pjtapchi.service.TaiKhoanService;




@RestController
@RequestMapping("api")
public class TestAPI {
    @Autowired
    private TaiKhoanService taiKhoanService;

    @Autowired
    private BangGiaQCService bangGiaQCService;

    @Autowired
    private HoaDonService hoaDonService;

    @GetMapping("users")
    public ResponseEntity<ApiResponse<List<Taikhoan>>> getExample() {
        List<Taikhoan> list = taiKhoanService.getAllTaiKhoans();
        System.out.println(list.get(0));
        ApiResponse<List<Taikhoan>> response = new ApiResponse<>(true, "Fetch user successful", list);
        if (!list.isEmpty()) {
            
        
            return ResponseEntity.ok().body(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

    }

    @Autowired
    EmailService emailService;

    @GetMapping("email")
    public String getMethodName() {
        emailService.sendActivationEmail("tvugiang@gmail.com", "aaa");
        return "Thanh cong";
    }
    

}
