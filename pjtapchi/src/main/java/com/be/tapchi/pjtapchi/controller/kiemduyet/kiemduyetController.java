package com.be.tapchi.pjtapchi.controller.kiemduyet;

import com.be.tapchi.pjtapchi.controller.apiResponse.ApiResponse;
import com.be.tapchi.pjtapchi.controller.kiemduyet.model.DTOKiemDuyet;
import com.be.tapchi.pjtapchi.controller.kiemduyet.model.DTOUser;
import com.be.tapchi.pjtapchi.jwt.JwtUtil;
import com.be.tapchi.pjtapchi.model.Baibao;
import com.be.tapchi.pjtapchi.model.Kiemduyet;
import com.be.tapchi.pjtapchi.model.Taikhoan;
import com.be.tapchi.pjtapchi.repository.BaiBaoRepository;
import com.be.tapchi.pjtapchi.repository.KiemduyetRepository;
import com.be.tapchi.pjtapchi.service.KiemduyetService;
import com.be.tapchi.pjtapchi.userRole.ManageRoles;

import jakarta.validation.Valid;

import java.time.LocalDate;
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

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<?>> createKiemDuyet(@Valid @RequestBody DTOKiemDuyet kiemDuyet,BindingResult bindingResult) {
        ApiResponse<?> api = new ApiResponse<>();
        if (kiemDuyet.getToken() == null) {
                api.setSuccess(false);
                api.setMessage(HttpStatus.NON_AUTHORITATIVE_INFORMATION.toString());

                return ResponseEntity.badRequest().body(api);
            }
            Taikhoan tk = jwtUtil.getTaikhoanFromToken(kiemDuyet.getToken());
            if (tk == null) {
                api.setSuccess(false);
                api.setMessage(HttpStatus.NON_AUTHORITATIVE_INFORMATION.toString());

                return ResponseEntity.badRequest().body(api);
            }
            if(!jwtUtil.checkRolesFromToken(kiemDuyet.getToken(), ManageRoles.getEDITORRole())){
                api.setSuccess(false);
                api.setMessage(HttpStatus.NON_AUTHORITATIVE_INFORMATION.toString());

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
        if(bb == null){
            api.setSuccess(false);
            api.setMessage("Lỗi không tìm thấy bài báo");

            return ResponseEntity.badRequest().body(api);
        }
        List<Kiemduyet> list = kiemduyetRepository.findByTaikhoan(tk);
        for (Kiemduyet kiemduyet2 : list) {
            if(kiemduyet2.getBaibao().getId().equals(bb.getId())){
            api.setSuccess(false);
            api.setMessage("Đã phân công người này");

            return ResponseEntity.badRequest().body(api);
            }
        }

        Kiemduyet kd = new Kiemduyet();
        bb.setStatus(1);
        kd.setBaibao(bb);
        kd.setGhichu(kiemDuyet.getGhichu());
        kd.setNgaykiemduyet(LocalDate.now());
        kd.setStatus(0);
        kd.setTaikhoan(tk);
        
        kiemDuyetService.saveKiemduyet(kd);
        Taikhoan tkuser = kd.getTaikhoan();
        DTOUser dtoUser = new DTOUser(tkuser.getHovaten(), String.valueOf(tkuser.getTaikhoan_id()));
        Map<String,Object> map = new HashMap<>();
        map.put("kiemduyet", kd);
        map.put("taikhoan", dtoUser);
        ApiResponse<?> response = new ApiResponse<>(true, "Create kiem duyet successful", map);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/update")
    public ResponseEntity<ApiResponse<Kiemduyet>> updateKiemDuyet(@RequestBody Kiemduyet kiemDuyet) {
        Kiemduyet kiemduyet = kiemDuyetService.saveKiemduyet(kiemDuyet);
        ApiResponse<Kiemduyet> response = new ApiResponse<>(true, "Update kiem duyet successful", kiemduyet);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<Boolean>> deleteKiemDuyet(@PathVariable("id") Long id) {
        kiemDuyetService.deleteKiemduyet(id);
        ApiResponse<Boolean> response = new ApiResponse<>(true, "Delete kiem duyet successful", true);
        return ResponseEntity.ok().body(response);
    }

}
