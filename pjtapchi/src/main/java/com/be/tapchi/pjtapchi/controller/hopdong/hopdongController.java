package com.be.tapchi.pjtapchi.controller.hopdong;

import com.be.tapchi.pjtapchi.controller.apiResponse.ApiResponse;
import com.be.tapchi.pjtapchi.controller.hopdong.DTO.ContractRequest;
import com.be.tapchi.pjtapchi.controller.hopdong.DTO.ContractResponseDTO;
import com.be.tapchi.pjtapchi.jwt.JwtUtil;
import com.be.tapchi.pjtapchi.model.BangGiaQC;
import com.be.tapchi.pjtapchi.model.HopDong;
import com.be.tapchi.pjtapchi.service.BangGiaQCService;
import com.be.tapchi.pjtapchi.service.HopDongService;
import com.be.tapchi.pjtapchi.userRole.ManageRoles;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;

@RestController
@RequestMapping("/contract")
public class hopdongController {
    @Autowired
    private EntityManager entityManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private HopDongService hopDongService;
    @Autowired
    private BangGiaQCService bangGiaQCService;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<HopDong>> getContract(@PathVariable("id") Long id) {
        HopDong contract = hopDongService.getHopDongById(id);
        ApiResponse<HopDong> response = new ApiResponse<>(true, "Fetch contract successful", contract);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<?>> createContract(@RequestBody ContractRequest request) {

        if (!jwtUtil.checkTokenAndTaiKhoan(request.getToken())) {
            ApiResponse<ContractResponseDTO> response = new ApiResponse<>(false, "Tài khoản không hợp lệ", null);
            return ResponseEntity.badRequest().body(response);
        }

        if (!jwtUtil.checkRolesFromToken(request.getToken(), ManageRoles.getCUSTOMERRole())) {
            ApiResponse<ContractResponseDTO> response = new ApiResponse<>(false, "Bạn Không Có Quyền Tạo Hợp Đồng", null);
            return ResponseEntity.badRequest().body(response);
        }
        // Retrieve BangGiaQC entity
        BangGiaQC bangGiaQC = bangGiaQCService.findBangGiaQCByBanggiaqc_id(request.getBgqcid());

        // Set new Contract
        HopDong contract = new HopDong();
        contract.setNgayBatDauHD(Date.valueOf(LocalDate.now()));
        contract.setNgayKetThucHD(Date.valueOf(LocalDate.now().plusDays(bangGiaQC.getSongay())));
        contract.setStatus(0);
        contract.setHoaDon(null);

        // Save contract
        hopDongService.saveHopDong(contract);

        // Create response DTO
        ContractResponseDTO contractResponseDTO = new ContractResponseDTO(contract, bangGiaQC);

        // Response
        ApiResponse<ContractResponseDTO> response = new ApiResponse<>(true, "Create contract successful, waiting for Payment", contractResponseDTO);
        return ResponseEntity.ok().body(response);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteBaibao(@PathVariable("id") Long id) {
        hopDongService.deleteHopDong(id);
        ApiResponse<Void> response = new ApiResponse<>(true, "Delete contract successful", null);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse<HopDong>> updateContract(@RequestBody HopDong contract) {
        HopDong updatedContract = hopDongService.updateHopDong(contract);
        ApiResponse<HopDong> response = new ApiResponse<>(true, "Update contract successful", updatedContract);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/updateStatus/{id}")
    public ResponseEntity<ApiResponse<Void>> updateStatus(@PathVariable("id") Long id,
                                                          @RequestParam("status") int status) {
        int updated = hopDongService.updateStatusById(id, status);
        ApiResponse<Void> response = new ApiResponse<>(updated > 0, "Update status successful", null);
        return ResponseEntity.ok().body(response);
    }

}