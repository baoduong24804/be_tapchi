package com.be.tapchi.pjtapchi.api;

import com.be.tapchi.pjtapchi.model.Taikhoan;
import com.be.tapchi.pjtapchi.service.BangGiaQCService;
import com.be.tapchi.pjtapchi.service.EmailService;
import com.be.tapchi.pjtapchi.service.HoaDonService;
import com.be.tapchi.pjtapchi.service.TaiKhoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("api")
public class TestAPI {
    @Autowired
    EmailService emailService;
    @Autowired
    private TaiKhoanService taiKhoanService;
    @Autowired
    private BangGiaQCService bangGiaQCService;
    @Autowired
    private HoaDonService hoaDonService;

    @GetMapping("users")
    public ResponseEntity<ApiResponse<List<Taikhoan>>> getExample() {
        List<Taikhoan> list = taiKhoanService.getAllTaiKhoans();
        ApiResponse<List<Taikhoan>> response = new ApiResponse<>(true, "Fetch successful", list);

        if (list.isEmpty()) {

            return ResponseEntity.ok().body(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

    }

    @GetMapping("email")
    public String getMethodName() {
        emailService.sendActivationEmail("numpadagain@gmail.com", "ádasdasdasd");
        return "Thanh cong";
    }


}
