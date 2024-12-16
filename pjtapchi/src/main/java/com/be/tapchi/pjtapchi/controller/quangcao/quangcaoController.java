package com.be.tapchi.pjtapchi.controller.quangcao;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.be.tapchi.pjtapchi.controller.apiResponse.ApiResponse;
import com.be.tapchi.pjtapchi.jwt.JwtUtil;
import com.be.tapchi.pjtapchi.model.QuangCao;
import com.be.tapchi.pjtapchi.model.Taikhoan;
import com.be.tapchi.pjtapchi.repository.QuangCaoRepository;
import com.be.tapchi.pjtapchi.repository.TaiKhoanRepository;
import com.be.tapchi.pjtapchi.userRole.ManageRoles;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("api/quangcao")
public class quangcaoController {

    @Autowired
    private QuangCaoRepository quangCaoRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("add/click/quangcao")
    public ResponseEntity<?> luotclick(@RequestBody(required = false) DTOQuangCao entity) {
        // TODO: process POST request

        if (entity.getQuangcaoId() == null) {
            ApiResponse<?> response = new ApiResponse<>(false, "getQuangcaoId trong", null);
            return ResponseEntity.badRequest().body(response);
        }
        if (entity.getQuangcaoId().isEmpty()) {
            ApiResponse<?> response = new ApiResponse<>(false, "getQuangcaoId trong", null);
            return ResponseEntity.badRequest().body(response);
        }

        QuangCao qc = quangCaoRepository.findById(Long.valueOf(entity.getQuangcaoId())).orElse(null);
        if (qc == null) {
            ApiResponse<?> response = new ApiResponse<>(false, "QuangCao trong", null);
            return ResponseEntity.badRequest().body(response);
        }
        try {
            if (qc.getLuotclick() == 0) {
                qc.setLuotclick(1);
            } else {
                qc.setLuotclick(qc.getLuotclick() + 1);
            }
        } catch (Exception e) {
            // TODO: handle exception
            qc.setLuotclick(0);
        }

        quangCaoRepository.save(qc);

        ApiResponse<?> response = new ApiResponse<>(true, "Them click thanh cong", qc.getLuotclick());
        return ResponseEntity.ok().body(response);

    }

    @Autowired
    private TaiKhoanRepository taiKhoanRepository;

    public String formatDate(String inputDateStr) {
        try {

            // Định dạng đầu vào (theo dạng "yyyy-MM-dd HH:mm:ss.S")
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
            Date inputDate = inputFormat.parse(inputDateStr);

            // Định dạng đầu ra (theo dạng "hh:mm:ss dd-MM-yyyy")
            SimpleDateFormat outputFormat = new SimpleDateFormat("hh:mm:ss dd-MM-yyyy");
            String formattedDate = outputFormat.format(inputDate);
            return formattedDate;
            // In kết quả
            // System.out.println("Định dạng mới: " + formattedDate);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    @PostMapping("update/user/quangcao")
    public ResponseEntity<?> updateqcc(@RequestBody(required = false) DTOQuangCao entity) {
        if (jwtUtil.checkTokenAndTaiKhoan(entity.getToken()) == false) {
            ApiResponse<?> response = new ApiResponse<>(false, "Tài khoản không hợp lệ", null);
            return ResponseEntity.badRequest().body(response);
        }

        Taikhoan tk = null;
        tk = jwtUtil.getTaikhoanFromToken(entity.getToken());

        if (tk == null) {
            ApiResponse<?> response = new ApiResponse<>(false, "Taikhoan trong", null);
            return ResponseEntity.badRequest().body(response);
        }

        if (entity.getLink() == null) {
            ApiResponse<?> response = new ApiResponse<>(false, "getLink trong", null);
            return ResponseEntity.badRequest().body(response);
        }
        if (entity.getLink().isEmpty()) {
            ApiResponse<?> response = new ApiResponse<>(false, "getLink trong", null);
            return ResponseEntity.badRequest().body(response);
        }

        if (entity.getUrl() == null) {
            ApiResponse<?> response = new ApiResponse<>(false, "getUrl trong", null);
            return ResponseEntity.badRequest().body(response);
        }
        if (entity.getUrl().isEmpty()) {
            ApiResponse<?> response = new ApiResponse<>(false, "getUrl trong", null);
            return ResponseEntity.badRequest().body(response);
        }

        if (entity.getTieude() == null) {
            ApiResponse<?> response = new ApiResponse<>(false, "getTieude trong", null);
            return ResponseEntity.badRequest().body(response);
        }
        if (entity.getTieude().isEmpty()) {
            ApiResponse<?> response = new ApiResponse<>(false, "getTieude trong", null);
            return ResponseEntity.badRequest().body(response);
        }

        if (entity.getQuangcaoId() == null) {
            ApiResponse<?> response = new ApiResponse<>(false, "getQuangcaoId trong", null);
            return ResponseEntity.badRequest().body(response);
        }
        if (entity.getQuangcaoId().isEmpty()) {
            ApiResponse<?> response = new ApiResponse<>(false, "getQuangcaoId trong", null);
            return ResponseEntity.badRequest().body(response);
        }

        QuangCao qc = quangCaoRepository.findById(Long.valueOf(entity.getQuangcaoId())).orElse(null);
        if (qc == null) {
            ApiResponse<?> response = new ApiResponse<>(false, "QuangCao trong", null);
            return ResponseEntity.badRequest().body(response);
        }

        qc.setTieude(entity.getTieude());
        qc.setLink(entity.getLink());
        qc.setUrl(entity.getUrl());
        if (qc.getStatus() == 1 || qc.getStatus() == 2) {
            qc.setStatus(1);
        }
        quangCaoRepository.save(qc);

        ApiResponse<?> response = new ApiResponse<>(true, "Cap nhat thanh cong", null);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("get/user/quangcao")
    public ResponseEntity<?> getquangcaobyuser(@RequestBody(required = false) DTOQuangCao entity) {
        // TODO: process POST request

        if (jwtUtil.checkTokenAndTaiKhoan(entity.getToken()) == false) {
            ApiResponse<?> response = new ApiResponse<>(false, "Tài khoản không hợp lệ", null);
            return ResponseEntity.badRequest().body(response);
        }

        // if (!jwtUtil.checkRolesFromToken(entity.getToken(),
        // ManageRoles.getADMINRole())) {
        // ApiResponse<?> response = new ApiResponse<>(false, "Can admin", null);
        // return ResponseEntity.badRequest().body(response);
        // }

        Taikhoan tk = null;
        tk = jwtUtil.getTaikhoanFromToken(entity.getToken());

        if (tk == null) {
            ApiResponse<?> response = new ApiResponse<>(false, "Taikhoan trong", null);
            return ResponseEntity.badRequest().body(response);
        }

        List<QuangCao> list = quangCaoRepository.findByTaikhoan(tk);
        System.out.println("Quang cao cua tk: " + tk.getTaikhoan_id());
        if (list == null) {
            ApiResponse<?> response = new ApiResponse<>(false, "List<QuangCao> trong", null);
            return ResponseEntity.badRequest().body(response);
        }
        List<DTOQuangCaoUser> listdata = new ArrayList<>();
        for (QuangCao quangCao : list) {
            DTOQuangCaoUser item = new DTOQuangCaoUser();
            item.setQuangcaoId(quangCao.getQuangcao_id() + "");
            item.setLink(quangCao.getLink());
            item.setUrl(quangCao.getUrl());
            item.setTieude(quangCao.getTieude());
            item.setLuotxem(quangCao.getLuotxem() + "");
            item.setLuotclick(quangCao.getLuotclick() + "");
            item.setGoiquangcao(quangCao.getBgqc().getTengoi());
            item.setNgaybd(formatDate(quangCao.getHopDong().getNgayBatDauHD().toString()));
            item.setNgaykt(formatDate(quangCao.getHopDong().getNgayKetThucHD().toString()));
            item.setStatus(quangCao.getStatus() + "");
            listdata.add(item);
        }

        ApiResponse<?> response = new ApiResponse<>(true, "Lay data thanh cong", listdata);
        return ResponseEntity.ok().body(response);

    }

    @PostMapping("add/xem/quangcao")
    public ResponseEntity<?> luotxem(@RequestBody(required = false) DTOQuangCao entity) {
        // TODO: process POST request

        if (entity.getQuangcaoId() == null) {
            ApiResponse<?> response = new ApiResponse<>(false, "getQuangcaoId trong", null);
            return ResponseEntity.badRequest().body(response);
        }
        if (entity.getQuangcaoId().isEmpty()) {
            ApiResponse<?> response = new ApiResponse<>(false, "getQuangcaoId trong", null);
            return ResponseEntity.badRequest().body(response);
        }

        QuangCao qc = quangCaoRepository.findById(Long.valueOf(entity.getQuangcaoId())).orElse(null);
        if (qc == null) {
            ApiResponse<?> response = new ApiResponse<>(false, "QuangCao trong", null);
            return ResponseEntity.badRequest().body(response);
        }
        try {
            if (qc.getLuotxem() == 0) {
                qc.setLuotxem(1);
            } else {
                qc.setLuotxem(qc.getLuotxem() + 1);
            }
        } catch (Exception e) {
            // TODO: handle exception
            qc.setLuotxem(0);
        }

        quangCaoRepository.save(qc);

        ApiResponse<?> response = new ApiResponse<>(true, "Them luot xem thanh cong", qc.getLuotclick());
        return ResponseEntity.ok().body(response);

    }

    @PostMapping("get/quangcao")
    public ResponseEntity<?> postMethodName() {
        // TODO: process POST request
        List<QuangCao> list = quangCaoRepository.findAll();
        List<DTOQuangCao> sList = new ArrayList<>();
        for (QuangCao quangCao : list) {
            if (quangCao.getStatus() != 2) {
                continue;
            }
            DTOQuangCao dto = new DTOQuangCao();
            dto.setBgqcId(quangCao.getBgqc().getBanggiaqc_id() + "");
            dto.setTengoi(quangCao.getTieude());
            dto.setUrl(quangCao.getUrl());
            dto.setLink(quangCao.getLink());
            dto.setQuangcaoId(quangCao.getQuangcao_id() + "");
            sList.add(dto);

        }

        if (sList.size() <= 0) {
            ApiResponse<?> response = new ApiResponse<>(true, "Du lieu null", sList);
            return ResponseEntity.ok().body(response);
        }

        ApiResponse<?> response = new ApiResponse<>(true, "Lay data thanh cong", sList);
        return ResponseEntity.ok().body(response);

    }

}
