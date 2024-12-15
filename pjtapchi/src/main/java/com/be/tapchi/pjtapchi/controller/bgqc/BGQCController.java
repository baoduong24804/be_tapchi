package com.be.tapchi.pjtapchi.controller.bgqc;

import com.be.tapchi.pjtapchi.controller.apiResponse.ApiResponse;

import com.be.tapchi.pjtapchi.model.HopDong;
import com.be.tapchi.pjtapchi.repository.BangGiaQCRepository;
import com.be.tapchi.pjtapchi.repository.HoaDonRepository;
import com.be.tapchi.pjtapchi.repository.HopDongRepository;
import com.be.tapchi.pjtapchi.service.BangGiaQCService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("api/bgqc")
public class BGQCController {

    @Autowired
    private BangGiaQCService bgqcService;

    @Autowired
    private BangGiaQCRepository bangGiaQCRepository;

    @Autowired
    private HoaDonRepository hoaDonRepository;

    @Autowired
    private HopDongRepository hopDongRepository;

    

    // Get all BangGiaQC
    @PostMapping("/all")
    public ResponseEntity<?> getAllBangGiaQC() {
        List<HopDong> hopdongs = hopDongRepository.findHopDongsWithHoaDonStatus(1);

        ApiResponse<?> response = new ApiResponse<>(false, "Tài khoản không hợp lệ", hopdongs);
        return ResponseEntity.ok().body(response);

    }
}
