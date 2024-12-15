package com.be.tapchi.pjtapchi.controller.bgqc;

import com.be.tapchi.pjtapchi.controller.apiResponse.ApiResponse;
import com.be.tapchi.pjtapchi.model.BangGiaQC;
import com.be.tapchi.pjtapchi.model.HopDong;
import com.be.tapchi.pjtapchi.model.QuangCao;
import com.be.tapchi.pjtapchi.repository.BangGiaQCRepository;
import com.be.tapchi.pjtapchi.repository.HoaDonRepository;
import com.be.tapchi.pjtapchi.repository.HopDongRepository;
import com.be.tapchi.pjtapchi.service.BangGiaQCService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
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

    @PostMapping("get/all")
    public ResponseEntity<?> postMethodName() {
        // TODO: process POST request
        List<HopDong> hopdongs = hopDongRepository.findHopDongsWithHoaDonStatus(1);
        int qc1 = 0;
        int qc2 = 0;
        int qc3 = 0;
        for (HopDong hopDong : hopdongs) {
            for (QuangCao qc : hopDong.getQuangCao()) {
                if (qc.getBgqc().getBanggiaqc_id() == 1) {
                    qc1 = 1;
                }
                if (qc.getBgqc().getBanggiaqc_id() == 2) {
                    qc2 = 1;
                }
                if (qc.getBgqc().getBanggiaqc_id() == 3) {
                    qc3 = 1;
                }
            }
        }
        
        BangGiaQC bgqc1 = bangGiaQCRepository.findById(Long.valueOf(1)).orElse(null);
        for (QuangCao qc : bgqc1.getQuangCao()) {
            System.out.println(qc.getHopDong().getNgayKetThucHD());
        }
        BangGiaQC bgqc2 = bangGiaQCRepository.findById(Long.valueOf(2)).orElse(null);
        BangGiaQC bgqc3 = bangGiaQCRepository.findById(Long.valueOf(3)).orElse(null);

        List<DTOBGQC> list = new ArrayList<>();

        if (qc1 == 1) {
            if (bgqc1 != null) {
                DTOBGQC bg = new DTOBGQC();
                bg.setBgqcId(bgqc1.getBanggiaqc_id() + "");
                bg.setSongay(bgqc1.getSongay() + "");
                bg.setTengoi(bgqc1.getTengoi());
                bg.setGiatien(bgqc1.getGiatien() + "");
                bg.setConqc(false);
                list.add(bg);
            }

        } else {
            if (bgqc1 != null) {
                DTOBGQC bg = new DTOBGQC();
                bg.setBgqcId(bgqc1.getBanggiaqc_id() + "");
                bg.setSongay(bgqc1.getSongay() + "");
                bg.setTengoi(bgqc1.getTengoi());
                bg.setGiatien(bgqc1.getGiatien() + "");
                bg.setConqc(true);
                list.add(bg);
            }
        }

        if (qc2 == 1) {
            if (bgqc2 != null) {
                DTOBGQC bg = new DTOBGQC();
                bg.setBgqcId(bgqc2.getBanggiaqc_id() + "");
                bg.setSongay(bgqc2.getSongay() + "");
                bg.setTengoi(bgqc2.getTengoi());
                bg.setGiatien(bgqc2.getGiatien() + "");
                bg.setConqc(false);
                list.add(bg);
            }

        } else {
            if (bgqc2 != null) {
                DTOBGQC bg = new DTOBGQC();
                bg.setBgqcId(bgqc2.getBanggiaqc_id() + "");
                bg.setSongay(bgqc2.getSongay() + "");
                bg.setTengoi(bgqc2.getTengoi());
                bg.setGiatien(bgqc2.getGiatien() + "");
                bg.setConqc(true);
                list.add(bg);
            }
        }

        if (qc3 == 1) {
            if (bgqc3 != null) {
                DTOBGQC bg = new DTOBGQC();
                bg.setBgqcId(bgqc3.getBanggiaqc_id() + "");
                bg.setSongay(bgqc3.getSongay() + "");
                bg.setTengoi(bgqc3.getTengoi());
                bg.setGiatien(bgqc3.getGiatien() + "");
                bg.setConqc(false);
                list.add(bg);
            }

        } else {
            if (bgqc3 != null) {
                DTOBGQC bg = new DTOBGQC();
                bg.setBgqcId(bgqc3.getBanggiaqc_id() + "");
                bg.setSongay(bgqc3.getSongay() + "");
                bg.setTengoi(bgqc3.getTengoi());
                bg.setGiatien(bgqc3.getGiatien() + "");
                bg.setConqc(true);
                list.add(bg);
            }
        }

        ApiResponse<?> response = new ApiResponse<>(true, "Lay data bgqc thanh cong",
                list);
        return ResponseEntity.ok().body(response);
    }

}
