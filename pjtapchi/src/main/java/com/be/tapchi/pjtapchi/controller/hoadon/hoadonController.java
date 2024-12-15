package com.be.tapchi.pjtapchi.controller.hoadon;

import com.be.tapchi.pjtapchi.controller.apiResponse.ApiResponse;
import com.be.tapchi.pjtapchi.controller.hoadon.DTO.HoaDonDTO;
import com.be.tapchi.pjtapchi.controller.hoadon.DTO.HoaDonDTO.HopDongDTO;
import com.be.tapchi.pjtapchi.jwt.JwtUtil;
import com.be.tapchi.pjtapchi.model.HoaDon;
import com.be.tapchi.pjtapchi.model.HopDong;
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
    public ResponseEntity<ApiResponse<HoaDonDTO>> getHoaDon(@PathVariable("id") Long id, @RequestBody String token) {
        if (jwtUtil.checkTokenAndTaiKhoan(token)) {
            ApiResponse<HoaDonDTO> response = new ApiResponse<>(false, "Tài khoản không hợp lệ", null);
            return ResponseEntity.badRequest().body(response);
        }

        if (jwtUtil.checkRolesFromToken(token, ManageRoles.getPARTNERRole())) {
            ApiResponse<HoaDonDTO> response = new ApiResponse<>(false, "Bạn Không Có Quyền Tạo Hợp Đồng", null);
            return ResponseEntity.badRequest().body(response);
        }

        HoaDon hoadon = hdService.findById(id);
        HoaDonDTO hoadonDTO = new HoaDonDTO();
        hoadonDTO.setHoadon_id(hoadon.getHoadon_id());
        hoadonDTO.setTongTien(Double.valueOf(hoadon.getTongTien()));
        hoadonDTO.setStatus(hoadon.getStatus());
        hoadonDTO.setNgayTao(hoadon.getNgayTao());
        hoadonDTO.setOrderCode(hoadon.getOrderCode());
        hoadonDTO.setHopDong(convertToHopDongDTO(hoadon.getHopDong()));

        ApiResponse<HoaDonDTO> response = new ApiResponse<>(true, "Fetch hoadon successful", hoadonDTO);
        return ResponseEntity.ok().body(response);
    }

    private HopDongDTO convertToHopDongDTO(HopDong hopDong) {
        HopDongDTO hopDongDTO = new HopDongDTO();
        hopDongDTO.setHopdong_id(hopDong.getHopdong_id());
        hopDongDTO.setNgayBatDauHD(hopDong.getNgayBatDauHD());
        hopDongDTO.setNgayKetThucHD(hopDong.getNgayKetThucHD());
        hopDongDTO.setStatus(hopDong.getStatus());
        return hopDongDTO;
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