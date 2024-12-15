package com.be.tapchi.pjtapchi.controller.hoadon;

import com.be.tapchi.pjtapchi.controller.apiResponse.ApiResponse;
import com.be.tapchi.pjtapchi.jwt.JwtUtil;
import com.be.tapchi.pjtapchi.model.HoaDon;
import com.be.tapchi.pjtapchi.model.Taikhoan;
import com.be.tapchi.pjtapchi.service.HoaDonService;
import com.be.tapchi.pjtapchi.userRole.ManageRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recipt")
public class hoadonController {

    @Autowired
    HoaDonService hdService;
    @Autowired
    JwtUtil jwtUtil;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<HoaDon>> getHoaDon(@PathVariable("id") Long id, @RequestBody String token) {
        if (jwtUtil.checkTokenAndTaiKhoan(token)) {
            ApiResponse<HoaDon> response = new ApiResponse<>(false, "Tài khoản không hợp lệ", null);
            return ResponseEntity.badRequest().body(response);
        }

        if (jwtUtil.checkRolesFromToken(token, ManageRoles.getPARTNERRole())) {
            ApiResponse<HoaDon> response = new ApiResponse<>(false, "Bạn Không Có Quyền Tạo Hợp Đồng", null);
            return ResponseEntity.badRequest().body(response);
        }

        HoaDon hoadon = hdService.findById(id);

        ApiResponse<HoaDon> response = new ApiResponse<>(true, "Fetch hoadon successful", hoadon);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/user")
    public ResponseEntity<ApiResponse<List<HoaDon>>> getHoaDonByUser(@RequestBody String token) {
        if (jwtUtil.checkTokenAndTaiKhoan(token)) {
            ApiResponse<List<HoaDon>> response = new ApiResponse<>(false, "Tài khoản không hợp lệ", null);
            return ResponseEntity.badRequest().body(response);
        }

        if (jwtUtil.checkRolesFromToken(token, ManageRoles.getPARTNERRole())) {
            ApiResponse<List<HoaDon>> response = new ApiResponse<>(false, "Bạn Không Có Quyền Tạo Hợp Đồng", null);
            return ResponseEntity.badRequest().body(response);
        }
        Taikhoan taikhoan = jwtUtil.getTaikhoanFromToken(token);

        List<HoaDon> hoadon = hdService.findHoaDonByTaikhoanTaikhoan_id(taikhoan.getTaikhoan_id());

        ApiResponse<List<HoaDon>> response = new ApiResponse<>(true, "Fetch hoadon successful", hoadon);
        return ResponseEntity.ok().body(response);
    }
}
