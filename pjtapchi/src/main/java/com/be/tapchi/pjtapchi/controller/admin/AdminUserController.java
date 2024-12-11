package com.be.tapchi.pjtapchi.controller.admin;

import com.be.tapchi.pjtapchi.controller.admin.DTOUser.DTOAdmin;
import com.be.tapchi.pjtapchi.controller.apiResponse.ApiResponse;
import com.be.tapchi.pjtapchi.jwt.JwtUtil;
import com.be.tapchi.pjtapchi.repository.TaiKhoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/admin")
public class AdminUserController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private TaiKhoanRepository taiKhoanRepository;


    @PostMapping("get/user")
    public ResponseEntity<?> postMethodName(@RequestBody(required = false) DTOAdmin entity) {
        //TODO: process POST request
        if (entity.getToken() == null) {
            ApiResponse<?> response = new ApiResponse<>(false, "Token trong", null);
            return ResponseEntity.badRequest().body(response);
        }
        if (entity.getToken().isEmpty()) {
            ApiResponse<?> response = new ApiResponse<>(false, "Token trong", null);
            return ResponseEntity.badRequest().body(response);
        }


        return null;
    }

}
