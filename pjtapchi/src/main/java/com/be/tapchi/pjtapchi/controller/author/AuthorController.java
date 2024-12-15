package com.be.tapchi.pjtapchi.controller.author;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.be.tapchi.pjtapchi.controller.admin.DTOUser.DTOResponse.DTOTaikhoan;
import com.be.tapchi.pjtapchi.controller.apiResponse.ApiResponse;
import com.be.tapchi.pjtapchi.jwt.JwtUtil;
import com.be.tapchi.pjtapchi.model.Baibao;
import com.be.tapchi.pjtapchi.model.Taikhoan;
import com.be.tapchi.pjtapchi.model.Thich;
import com.be.tapchi.pjtapchi.repository.BaiBaoRepository;
import com.be.tapchi.pjtapchi.userRole.ManageRoles;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("api/author")
public class AuthorController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private BaiBaoRepository baiBaoRepository;

    public String formatDDMMYYYY(String inputDate) {
        String formattedDate = "";
        try {
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

            LocalDate date = LocalDate.parse(inputDate, inputFormatter);
            formattedDate = date.format(outputFormatter);
            return formattedDate;
        } catch (Exception e) {
            // TODO: handle exception
            return formattedDate;
        }

        // System.out.println(formattedDate); // Output: 27-11-2024
        
    }

    @PostMapping("get/baibao")
    public ResponseEntity<?> getBaibao(@RequestBody(required = false) DTOAuthor entity,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size) {
        // TODO: process POST request

        if (jwtUtil.checkTokenAndTaiKhoan(entity.getToken()) == false) {
            ApiResponse<?> response = new ApiResponse<>(false, "Tài khoản không hợp lệ", null);
            return ResponseEntity.badRequest().body(response);
        }

        if (!jwtUtil.checkRolesFromToken(entity.getToken(), ManageRoles.getADMINRole(), ManageRoles.getAUTHORRole())) {
            ApiResponse<?> response = new ApiResponse<>(false, "Can author", null);
            return ResponseEntity.badRequest().body(response);
        }

        Pageable pageable = PageRequest.of(page, size);
        Taikhoan tk = null;
        tk = jwtUtil.getTaikhoanFromToken(entity.getToken());
        if (tk == null) {
            ApiResponse<?> response = new ApiResponse<>(false, "Ko tim thay tk", null);
            return ResponseEntity.badRequest().body(response);
        }
        Page<Baibao> list = baiBaoRepository.findBaibaoByTaikhoan(tk, pageable);
        if (list.getContent().size() <= 0) {
            ApiResponse<?> response = new ApiResponse<>(true, "Du lieu null", null);
            return ResponseEntity.ok().body(response);
        }

        Map<Object, Object> data = new HashMap<>();

        List<DTOBaibao> baibaos = new ArrayList<>();
        for (Baibao baibao : list.getContent()) {
            DTOBaibao u = new DTOBaibao();

            DTOTheloai tl = new DTOTheloai();

            tl.setTheloaiId(baibao.getTheloai().getId() + "");
            tl.setTentheloai(baibao.getTheloai().getTenloai());

            u.setTheloai(tl);

            u.setBaibaoId(baibao.getId() + "");
            u.setTieude(baibao.getTieude());
            u.setNoidung(baibao.getNoidung());
            u.setNgaytao(formatDDMMYYYY(baibao.getNgaytao() + ""));
            u.setNgaydang(formatDDMMYYYY(baibao.getNgaydang() + ""));
            u.setStatus(baibao.getStatus() + "");
            u.setUrl(baibao.getUrl());
            u.setFile(baibao.getFile());
            u.setLichsu(baibao.getLichsu());

            int slike = 0;
            for (Thich thich : baibao.getThichs()) {
                if (thich.getStatus() == 1) {
                    slike++;
                }
            }
            u.setLuotthich(slike + "");

            int sxem = 0;
            if (baibao.getThichs() != null) {
                if (baibao.getThichs().size() > 0) {
                    sxem = baibao.getThichs().size();
                }
            }

            u.setLuotxem(sxem + "");

            baibaos.add(u);
        }

        data.put("data", baibaos);

        Map<String, Object> phantrang = new HashMap<>();
        phantrang.put("totalPage", String.valueOf(list.getTotalPages()));
        phantrang.put("pageNumber", String.valueOf(list.getNumber()));
        phantrang.put("pageSize", String.valueOf(list.getSize()));
        phantrang.put("totalElements", String.valueOf(list.getTotalElements()));
        data.put("phantrang", phantrang);

        ApiResponse<?> response = new ApiResponse<>(true, "Lay data thanh cong", data);
        return ResponseEntity.ok().body(response);
    }

}
