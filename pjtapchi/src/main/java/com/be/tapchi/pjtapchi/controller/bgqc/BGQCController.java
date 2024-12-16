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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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

    public static boolean isTimeOverdue(String targetDateStr) {
        // Định dạng chuỗi thời gian
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");

        // Lấy thời gian hiện tại
        LocalDateTime currentDateTime = LocalDateTime.now();

        // Chuyển chuỗi thời gian đích thành LocalDateTime
        LocalDateTime targetDateTime = LocalDateTime.parse(targetDateStr, formatter);

        // So sánh thời gian hiện tại với thời gian đích
        return currentDateTime.isAfter(targetDateTime); // Trả về true nếu đã quá thời gian
    }

    public static String formatToVND(double amount) {
        // Tạo định dạng tiền tệ Việt Nam
        NumberFormat vndFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

        // Trả về chuỗi định dạng số tiền
        return vndFormat.format(amount);
    }

    // @PostMapping("/get/all")
    // public ResponseEntity<?> getAllBGQC() {
    //     List<BangGiaQC> bgqcs = bgqcService.getAllBGQC();
    //     ApiResponse<?> response = new ApiResponse<>(true, "Lấy dữ liệu BGQC thành công", bgqcs);
    //     return ResponseEntity.ok().body(response);
    // }

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
        // List<HopDong> hopdongs = hopDongRepository.findHopDongsWithHoaDonStatus(1);
        int qc1 = 0;
        int qc2 = 0;
        int qc3 = 0;
        // for (HopDong hopDong : hopdongs) {
        // for (QuangCao qc : hopDong.getQuangCao()) {
        // if (qc.getBgqc().getBanggiaqc_id() == 1) {
        // qc1 = 1;
        // }
        // if (qc.getBgqc().getBanggiaqc_id() == 2) {
        // qc2 = 1;
        // }
        // if (qc.getBgqc().getBanggiaqc_id() == 3) {
        // qc3 = 1;
        // }
        // }
        // }

        BangGiaQC bgqc1 = bangGiaQCRepository.findById(Long.valueOf(1)).orElse(null);
        if (bgqc1 != null) {
            for (QuangCao qc : bgqc1.getQuangCao()) {
                // System.out.println(qc.getHopDong().getNgayKetThucHD());
                if(qc.getHopDong() == null){
                    continue;
                }
                if (qc.getHopDong().getStatus() == 1) {
                    if (isTimeOverdue(qc.getHopDong().getNgayKetThucHD().toString())) {
                        qc1 = 1;
                    }
                }
                // else{
                // System.out.println("Con han");
                // }
            }
        }

        BangGiaQC bgqc2 = bangGiaQCRepository.findById(Long.valueOf(2)).orElse(null);
        if (bgqc2 != null) {
            for (QuangCao qc : bgqc2.getQuangCao()) {
                // System.out.println(qc.getHopDong().getNgayKetThucHD());
                if(qc.getHopDong() == null){
                    continue;
                }
                if (qc.getHopDong().getStatus() == 1) {
                    if (isTimeOverdue(qc.getHopDong().getNgayKetThucHD().toString())) {
                        qc2 = 1;
                    }
                }
                // else{
                // System.out.println("Con han");
                // }
            }
        }

        BangGiaQC bgqc3 = bangGiaQCRepository.findById(Long.valueOf(3)).orElse(null);
        if (bgqc3 != null) {
            for (QuangCao qc : bgqc3.getQuangCao()) {
                if(qc.getHopDong() == null){
                    continue;
                }
                // System.out.println(qc.getHopDong().getNgayKetThucHD());
                if (qc.getHopDong().getStatus() == 1) {
                    if (isTimeOverdue(qc.getHopDong().getNgayKetThucHD().toString())) {
                        qc3 = 1;
                    }
                }
                // else{
                // System.out.println("Con han");
                // }
            }
        }

        List<DTOBGQC> list = new ArrayList<>();

        if (qc1 == 1) {
            if (bgqc1 != null) {
                DTOBGQC bg = new DTOBGQC();
                bg.setBgqcId(bgqc1.getBanggiaqc_id() + "");
                bg.setSongay(bgqc1.getSongay() + "");
                bg.setTengoi(bgqc1.getTengoi());
                bg.setGiatien(formatToVND(bgqc1.getGiatien()));
                bg.setConqc(false);
                list.add(bg);
            }

        } else {
            if (bgqc1 != null) {
                DTOBGQC bg = new DTOBGQC();
                bg.setBgqcId(bgqc1.getBanggiaqc_id() + "");
                bg.setSongay(bgqc1.getSongay() + "");
                bg.setTengoi(bgqc1.getTengoi());
                bg.setGiatien(formatToVND(bgqc1.getGiatien()));
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
                bg.setGiatien(formatToVND(bgqc2.getGiatien()));
                bg.setConqc(false);
                list.add(bg);
            }

        } else {
            if (bgqc2 != null) {
                DTOBGQC bg = new DTOBGQC();
                bg.setBgqcId(bgqc2.getBanggiaqc_id() + "");
                bg.setSongay(bgqc2.getSongay() + "");
                bg.setTengoi(bgqc2.getTengoi());
                bg.setGiatien(formatToVND(bgqc2.getGiatien()));
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
                bg.setGiatien(formatToVND(bgqc3.getGiatien()));
                bg.setConqc(false);
                list.add(bg);
            }

        } else {
            if (bgqc3 != null) {
                DTOBGQC bg = new DTOBGQC();
                bg.setBgqcId(bgqc3.getBanggiaqc_id() + "");
                bg.setSongay(bgqc3.getSongay() + "");
                bg.setTengoi(bgqc3.getTengoi());
                bg.setGiatien(formatToVND(bgqc3.getGiatien()));
                bg.setConqc(true);
                list.add(bg);
            }
        }

        ApiResponse<?> response = new ApiResponse<>(true, "Lay data bgqc thanh cong",
                list);
        return ResponseEntity.ok().body(response);
    }

}
