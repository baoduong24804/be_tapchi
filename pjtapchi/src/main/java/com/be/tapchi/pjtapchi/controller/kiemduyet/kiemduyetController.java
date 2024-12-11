package com.be.tapchi.pjtapchi.controller.kiemduyet;

import com.be.tapchi.pjtapchi.controller.apiResponse.ApiResponse;
import com.be.tapchi.pjtapchi.controller.kiemduyet.model.DTOBaiBao;
import com.be.tapchi.pjtapchi.controller.kiemduyet.model.DTOKiemDuyet;
import com.be.tapchi.pjtapchi.controller.kiemduyet.model.DTOResult;
import com.be.tapchi.pjtapchi.controller.kiemduyet.model.DTOUpdate;
import com.be.tapchi.pjtapchi.controller.kiemduyet.model.DTOTaiKhoan;
import com.be.tapchi.pjtapchi.controller.kiemduyet.model.DTOTheLoai;
import com.be.tapchi.pjtapchi.controller.kiemduyet.model.DTOToken;
import com.be.tapchi.pjtapchi.controller.kiemduyet.model.DTOUser;
import com.be.tapchi.pjtapchi.jwt.JwtUtil;
import com.be.tapchi.pjtapchi.model.Baibao;
import com.be.tapchi.pjtapchi.model.Kiemduyet;
import com.be.tapchi.pjtapchi.model.Taikhoan;
import com.be.tapchi.pjtapchi.repository.BaiBaoRepository;
import com.be.tapchi.pjtapchi.repository.KiemduyetRepository;
import com.be.tapchi.pjtapchi.repository.TaiKhoanRepository;
import com.be.tapchi.pjtapchi.service.KiemduyetService;
import com.be.tapchi.pjtapchi.userRole.ManageRoles;

import jakarta.validation.Valid;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/censor")
public class kiemduyetController {

    @Autowired
    private KiemduyetService kiemDuyetService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private TaiKhoanRepository taiKhoanRepository;

    @Autowired
    private BaiBaoRepository baiBaoRepository;

    @Autowired
    private KiemduyetRepository kiemduyetRepository;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<Page<Kiemduyet>>> getAllKiemDuyet(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Kiemduyet> pageResult = kiemDuyetService.getAllKiemduyets(pageable);
        ApiResponse<Page<Kiemduyet>> response = new ApiResponse<>(true, "Fetch Kiem Duyet successful", pageResult);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Kiemduyet>> getKiemDuyetById(@PathVariable("id") Integer id) {
        Kiemduyet kiemduyet = kiemDuyetService.getKiemduyetById(id);
        ApiResponse<Kiemduyet> response = new ApiResponse<>(true, "Fetch kiem duyet successful", kiemduyet);
        return ResponseEntity.ok().body(response);
    }

    public boolean checkToken(String token) {
        try {
            if (token.isBlank() || token == null) {

                return false;
            }
            Taikhoan tk = jwtUtil.getTaikhoanFromToken(token);
            if (!jwtUtil.checkRolesFromToken(token, ManageRoles.getEDITORRole())) {

                return false;
            }
            if (tk == null) {

                return false;
            }
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
            return false;
        }

        return true;
    }

    @PostMapping("get/kiemduyet")
    public ResponseEntity<ApiResponse<?>> getKiemDuyet(@RequestBody DTOToken kiemDuyet,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size) {
        // TODO: process POST request
        ApiResponse<?> api = new ApiResponse<>();
        if (jwtUtil.checkTokenAndTaiKhoan(kiemDuyet.getToken()) ==  false) {
            ApiResponse<?> response = new ApiResponse<>(false, "Tài khoản không hợp lệ", null);
            return ResponseEntity.badRequest().body(response);
        }

        if(!jwtUtil.checkRolesFromToken(kiemDuyet.getToken(), ManageRoles.getADMINRole(), ManageRoles.getEDITORRole(),ManageRoles.getCENSORRole())){
            ApiResponse<?> response = new ApiResponse<>(false, "Can quyen admin, editor, censor", null);
            return ResponseEntity.badRequest().body(response);
        }

        Pageable pageable = PageRequest.of(page, size);
        Page<Kiemduyet> result;
        if(kiemDuyet.getStatus() == null || kiemDuyet.getStatus().isBlank()){
            result = kiemduyetRepository.findAll(pageable);
        }else{
            Integer st = Integer.valueOf(kiemDuyet.getStatus());
            System.out.println("get by status = "+st);
            result = kiemduyetRepository.findByStatus(st, pageable);
        }


        
        Map<String, Object> data = new HashMap<>();

        List<DTOResult> lKiemDuyets = new ArrayList<>();

        for (Kiemduyet item : result.getContent()) {
            DTOResult dto = new DTOResult();
            dto.setId(String.valueOf(item.getId()));
            dto.setNgaytao(item.getNgaykiemduyet());
            dto.setStatus(item.getStatus());
            dto.setGhichu(item.getGhichu());

            DTOBaiBao dtoBaiBao = new DTOBaiBao();
            dtoBaiBao.setId(String.valueOf(item.getBaibao().getId()));
            dtoBaiBao.setTieude(item.getBaibao().getTieude());
            dtoBaiBao.setNgaytao(item.getBaibao().getNgaytao());
            dtoBaiBao.setFile(item.getBaibao().getFile());
            dtoBaiBao.setUrl(item.getBaibao().getUrl());

            DTOTaiKhoan dtoTaiKhoan = new DTOTaiKhoan();
            dtoTaiKhoan.setId(String.valueOf(item.getTaikhoan().getTaikhoan_id()));
            dtoTaiKhoan.setHovaten(item.getTaikhoan().getHovaten());

            DTOTheLoai dtoTheLoai = new DTOTheLoai();

            dtoTheLoai.setId(String.valueOf(item.getBaibao().getTheloai().getId()));
            dtoTheLoai.setTheloai(item.getBaibao().getTheloai().getTenloai());

            dtoBaiBao.setTaiKhoan(dtoTaiKhoan);

            dto.setBaiBao(dtoBaiBao);

            lKiemDuyets.add(dto);
        }

        data.put("data", lKiemDuyets);

        api.setSuccess(true);
        api.setMessage("Lấy data thành công");
        api.setData(data);
        return ResponseEntity.ok().body(api);

    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<?>> createKiemDuyet(@Valid @RequestBody DTOKiemDuyet kiemDuyet,
            BindingResult bindingResult) {
        ApiResponse<?> api = new ApiResponse<>();

        try {
            if (jwtUtil.checkTokenAndTaiKhoan(kiemDuyet.getToken()) ==  false) {
                ApiResponse<?> response = new ApiResponse<>(false, "Tài khoản không hợp lệ", null);
                return ResponseEntity.badRequest().body(response);
            }


            // Taikhoan tk = jwtUtil.getTaikhoanFromToken(kiemDuyet.getToken());
            
            if(!jwtUtil.checkRolesFromToken(kiemDuyet.getToken(), ManageRoles.getADMINRole(), ManageRoles.getEDITORRole(),ManageRoles.getCENSORRole())){
                ApiResponse<?> response = new ApiResponse<>(false, "Can quyen admin, editor, censor", null);
                return ResponseEntity.badRequest().body(response);
            }


        } catch (Exception e) {
            // TODO: handle exception
            api.setSuccess(false);
            api.setMessage("Lỗi không mong muốn");
            api.setData(null);
            return ResponseEntity.badRequest().body(api);
        }

        // kiem tra du lieu bi trong
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getFieldErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.joining(", "));
            api.setSuccess(false);
            api.setMessage("Không được để trống dữ liệu");
            api.setData(errorMessage);
            return ResponseEntity.badRequest().body(api);
        }

        Baibao bb = baiBaoRepository.findBaibaoById(Integer.valueOf(kiemDuyet.getBaibaoId()));
        if (bb == null) {
            api.setSuccess(false);
            api.setMessage("Lỗi không tìm thấy bài báo");

            return ResponseEntity.badRequest().body(api);
        }

        Taikhoan tk_phancong = taiKhoanRepository.findById(Long.valueOf(kiemDuyet.getTaikhoanId())).orElse(null);
        if (tk_phancong == null) {
            api.setSuccess(false);
            api.setMessage("Lỗi khi tìm người kiểm duyệt");
            api.setData(null);
            return ResponseEntity.badRequest().body(api);
        }

        List<Kiemduyet> list = kiemduyetRepository.findByTaikhoan(tk_phancong);
        for (Kiemduyet kiemduyet2 : list) {
            if (kiemduyet2.getBaibao().getId().equals(bb.getId())) {
                api.setSuccess(true);
                api.setMessage("Đã phân công người này");
                api.setData(null);
                return ResponseEntity.ok().body(api);
            }
        }

        // kiem tra bai bao da dc phan cong chua
        List<Kiemduyet> list2 = kiemduyetRepository.findByBaibao(bb);
        if (list != null || list2.size() > 0) {
            for (Kiemduyet ikd : list2) {
                // nguoi kiem duyet hien tai != nguoi moi
                if (!ikd.getTaikhoan().getTaikhoan_id().equals(tk_phancong.getTaikhoan_id())) {
                    ikd.setTaikhoan(tk_phancong);
                    kiemduyetRepository.save(ikd);
                    api.setSuccess(true);
                    api.setMessage("Đã thay đổi người kiểm duyệt");
                    api.setData(null);
                    return ResponseEntity.ok().body(api);
                }
            }
        }

        Kiemduyet kd = new Kiemduyet();
        bb.setStatus(2);
        kd.setBaibao(bb);
        kd.setGhichu(kiemDuyet.getGhichu());
        kd.setNgaykiemduyet(LocalDate.now());
        kd.setStatus(0);
        kd.setTaikhoan(tk_phancong);

        kiemDuyetService.saveKiemduyet(kd);
        Taikhoan tkuser = kd.getTaikhoan();
        DTOUser dtoUser = new DTOUser(tkuser.getHovaten(), String.valueOf(tkuser.getTaikhoan_id()));
        Map<String, Object> map = new HashMap<>();
        map.put("kiemduyet", kd);
        map.put("taikhoan", dtoUser);
        ApiResponse<?> response = new ApiResponse<>(true, "Create kiem duyet successful", map);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/update")
    public ResponseEntity<ApiResponse<?>> updateKiemDuyet(@RequestBody(required = false) DTOUpdate entity) {
        ApiResponse<?> api = new ApiResponse<>();
        try {
            if (entity.getToken() == null) {
                api.setSuccess(false);
                api.setMessage(HttpStatus.NON_AUTHORITATIVE_INFORMATION.toString());

                return ResponseEntity.badRequest().body(api);
            }
            Taikhoan tk = jwtUtil.getTaikhoanFromToken(entity.getToken());
            if (tk == null) {
                api.setSuccess(false);
                api.setMessage(HttpStatus.NON_AUTHORITATIVE_INFORMATION.toString());

                return ResponseEntity.badRequest().body(api);
            }
            if (!jwtUtil.checkRolesFromToken(entity.getToken(), ManageRoles.getEDITORRole())) {
                api.setSuccess(false);
                api.setMessage(HttpStatus.NON_AUTHORITATIVE_INFORMATION.toString());

                return ResponseEntity.badRequest().body(api);
            }
            if(entity.getStatus() == null){
                api.setSuccess(false);
                api.setMessage("Lỗi trống dữ liệu");

                return ResponseEntity.badRequest().body(api);
            }
        } catch (Exception e) {
            // TODO: handle exception
            api.setSuccess(false);
            api.setMessage("Lỗi không mong muốn");
            api.setData(e.getMessage());
            return ResponseEntity.badRequest().body(api);
        }

        try {
            Kiemduyet kd = kiemduyetRepository.findById(Long.valueOf(entity.getKiemduyetId())).orElse(null);
            if (kd == null) {
                api.setSuccess(false);
                api.setMessage("Lỗi không tìm thấy kiểm duyệt");
                api.setData(null);
                return ResponseEntity.badRequest().body(api);
            }
            Baibao bb = kd.getBaibao();
            if(bb == null){
                api.setSuccess(false);
                api.setMessage("Lỗi không tìm thấy bài báo");
                api.setData(null);
                return ResponseEntity.badRequest().body(api);
            }
            bb.setStatus(3);
            kd.setStatus(Integer.valueOf(entity.getStatus()));
            kd.setGhichu(entity.getGhichu());
            kd.setNgaykiemduyet(LocalDate.now());

            kiemduyetRepository.save(kd);

            api.setSuccess(true);
            api.setMessage("Cập nhật thành công");
            api.setData(null);
            return ResponseEntity.ok().body(api);

        } catch (Exception e) {
            // TODO: handle exception
            api.setSuccess(false);
            api.setMessage("Lỗi không mong muốn 2");
            api.setData(e.getMessage());
            return ResponseEntity.badRequest().body(api);
        }

      
    }

    @PostMapping("/update/kiemduyet/baibao")
    public ResponseEntity<ApiResponse<?>> updateBaiBao(@RequestBody(required = false) DTOUpdate entity) {
        ApiResponse<?> api = new ApiResponse<>();
        try {
            if (entity.getToken() == null) {
                api.setSuccess(false);
                api.setMessage(HttpStatus.NON_AUTHORITATIVE_INFORMATION.toString());

                return ResponseEntity.badRequest().body(api);
            }
            Taikhoan tk = jwtUtil.getTaikhoanFromToken(entity.getToken());
            if (tk == null) {
                api.setSuccess(false);
                api.setMessage(HttpStatus.NON_AUTHORITATIVE_INFORMATION.toString());

                return ResponseEntity.badRequest().body(api);
            }
            if (!jwtUtil.checkRolesFromToken(entity.getToken(), ManageRoles.getEDITORRole())) {
                api.setSuccess(false);
                api.setMessage("Yêu cầu quyền EDITOR");

                return ResponseEntity.badRequest().body(api);
            }
            if(entity.getStatus() == null){
                api.setSuccess(false);
                api.setMessage("Lỗi trống dữ liệu");

                return ResponseEntity.badRequest().body(api);
            }
        } catch (Exception e) {
            // TODO: handle exception
            api.setSuccess(false);
            api.setMessage("Lỗi không mong muốn");
            api.setData(e.getMessage());
            return ResponseEntity.badRequest().body(api);
        }

        try {
            Kiemduyet kd = kiemduyetRepository.findById(Long.valueOf(entity.getKiemduyetId())).orElse(null);
            if (kd == null) {
                api.setSuccess(false);
                api.setMessage("Lỗi không tìm thấy kiểm duyệt");
                api.setData(null);
                return ResponseEntity.badRequest().body(api);
            }
            Baibao bb = kd.getBaibao();
            if(bb == null){
                api.setSuccess(false);
                api.setMessage("Lỗi không tìm thấy bài báo");
                api.setData(null);
                return ResponseEntity.badRequest().body(api);
            }
            bb.setStatus(Integer.valueOf(entity.getStatus()));
            //kd.setStatus(Integer.valueOf(entity.getStatus()));
            kd.setGhichu(entity.getGhichu());
            //kd.setNgaykiemduyet(LocalDate.now());

            kiemduyetRepository.save(kd);

            api.setSuccess(true);
            api.setMessage("Cập nhật thành công");
            api.setData(null);
            return ResponseEntity.ok().body(api);

        } catch (Exception e) {
            // TODO: handle exception
            api.setSuccess(false);
            api.setMessage("Lỗi không mong muốn 2");
            api.setData(e.getMessage());
            return ResponseEntity.badRequest().body(api);
        }

      
    }

    // @PostMapping("bientapvien/phanhoi")
    // public ResponseEntity<ApiResponse<?>> btvPhanHoi(@RequestBody(required = false) DTOUpdate entity) {
    //     ApiResponse<?> api = new ApiResponse<>();
    //     try {
    //         if (entity.getToken() == null) {
    //             api.setSuccess(false);
    //             api.setMessage(HttpStatus.NON_AUTHORITATIVE_INFORMATION.toString());

    //             return ResponseEntity.badRequest().body(api);
    //         }
    //         Taikhoan tk = jwtUtil.getTaikhoanFromToken(entity.getToken());
    //         if (tk == null) {
    //             api.setSuccess(false);
    //             api.setMessage(HttpStatus.NON_AUTHORITATIVE_INFORMATION.toString());

    //             return ResponseEntity.badRequest().body(api);
    //         }
    //         if (!jwtUtil.checkRolesFromToken(entity.getToken(), ManageRoles.getEDITORRole())) {
    //             api.setSuccess(false);
    //             api.setMessage(HttpStatus.NON_AUTHORITATIVE_INFORMATION.toString());

    //             return ResponseEntity.badRequest().body(api);
    //         }
    //         if(entity.getStatus() == null){
    //             api.setSuccess(false);
    //             api.setMessage("Lỗi trống dữ liệu");

    //             return ResponseEntity.badRequest().body(api);
    //         }
    //     } catch (Exception e) {
    //         // TODO: handle exception
    //         api.setSuccess(false);
    //         api.setMessage("Lỗi không mong muốn");
    //         api.setData(null);
    //         return ResponseEntity.badRequest().body(api);
    //     }

    //     try {
    //         Kiemduyet kd = kiemduyetRepository.findById(Long.valueOf(entity.getKiemduyetId())).orElse(null);
    //         if (kd == null) {
    //             api.setSuccess(false);
    //             api.setMessage("Lỗi không tìm thấy kiểm duyệt");
    //             api.setData(null);
    //             return ResponseEntity.badRequest().body(api);
    //         }
    //         Baibao bb = kd.getBaibao();
    //         if(bb == null){
    //             api.setSuccess(false);
    //             api.setMessage("Lỗi không tìm thấy bài báo");
    //             api.setData(null);
    //             return ResponseEntity.badRequest().body(api);
    //         }
    //         bb.setStatus(3);
    //         kd.setStatus(Integer.valueOf(entity.getStatus()));
    //         kd.setGhichu(entity.getGhichu());
    //         kd.setNgaykiemduyet(LocalDate.now());

    //         kiemduyetRepository.save(kd);

    //         api.setSuccess(true);
    //         api.setMessage("Cập nhật thành công");
    //         api.setData(null);
    //         return ResponseEntity.ok().body(api);

    //     } catch (Exception e) {
    //         // TODO: handle exception
    //         api.setSuccess(false);
    //         api.setMessage("Lỗi không mong muốn 2");
    //         api.setData(null);
    //         return ResponseEntity.badRequest().body(api);
    //     }

      
    // }

    @PostMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<Boolean>> deleteKiemDuyet(@PathVariable("id") Long id) {
        kiemDuyetService.deleteKiemduyet(id);
        ApiResponse<Boolean> response = new ApiResponse<>(true, "Delete kiem duyet successful", true);
        return ResponseEntity.ok().body(response);
    }

}
