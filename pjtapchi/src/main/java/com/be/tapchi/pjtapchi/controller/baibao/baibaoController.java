package com.be.tapchi.pjtapchi.controller.baibao;

import com.be.tapchi.pjtapchi.controller.apiResponse.ApiResponse;
import com.be.tapchi.pjtapchi.controller.baibao.model.BaibaoResponseDTO;
import com.be.tapchi.pjtapchi.controller.baibao.model.DTOBaiBao;
import com.be.tapchi.pjtapchi.controller.baibao.model.DTOTacGia;
import com.be.tapchi.pjtapchi.controller.theloai.theloaiController;
import com.be.tapchi.pjtapchi.jwt.JwtUtil;
import com.be.tapchi.pjtapchi.model.*;
import com.be.tapchi.pjtapchi.repository.TheloaiRepository;
import com.be.tapchi.pjtapchi.service.BaibaoService;
import com.be.tapchi.pjtapchi.service.BinhluanService;
import com.be.tapchi.pjtapchi.userRole.ManageRoles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/baibao")
public class baibaoController {

    @Autowired
    BinhluanService BinhluanService;

    @Autowired
    private BaibaoService bbService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private TheloaiRepository theloaiRepository;

    @PostMapping("/get/baibao/all/editor")
    public ResponseEntity<?> getAllBaibao(
            @RequestBody(required = false) DTOTacGia entity,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size) {
        try {
            if (entity.getToken().isBlank() || entity.getToken() == null) {
                ApiResponse<?> response = new ApiResponse<>(false, "Lỗi token không hợp lệ", null);
                return ResponseEntity.badRequest().body(response);
            }
            Taikhoan tk = jwtUtil.getTaikhoanFromToken(entity.getToken());
            if(!jwtUtil.checkRolesFromToken(entity.getToken(), ManageRoles.getEDITORRole())){
                ApiResponse<?> response = new ApiResponse<>(false, "Không được phép truy cập", null);
                return ResponseEntity.badRequest().body(response);
            }
            if (tk == null) {
                ApiResponse<?> response = new ApiResponse<>(false, "Lỗi tài khoản không hợp lệ", null);
                return ResponseEntity.badRequest().body(response);
            }
            Pageable pageable = PageRequest.of(page, size);
            Page<Baibao> pageResult = bbService.findAllBaibao(pageable);
            
            // Page<BaibaoResponseDTO> responsePage = pageResult.map(this::convertToDTO);
            ApiResponse<Page<?>> response = new ApiResponse<>(true, "Fetch bai bao successful",
                    pageResult);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            // TODO: handle exception
            ApiResponse<Page<?>> response = new ApiResponse<>(true, "Loi lay dsbaibao theo tai khoan",
            null);
    return ResponseEntity.ok().body(response);
        }

    }

    @GetMapping("get/baibao/{id}")
    public ResponseEntity<ApiResponse<?>> getExample(@PathVariable("id") Integer id) {
        Baibao bb = bbService.getBaibaoById(id);
        if (bb != null) {
            // BaibaoResponseDTO responseDTO = convertToDTO(bb);
            ApiResponse<?> response = new ApiResponse<>(true, "Fetch bai bao successful", bb);
            return ResponseEntity.ok().body(response);
        } else {
            ApiResponse<BaibaoResponseDTO> response = new ApiResponse<>(true, "Fetch bai bao successful", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("/author/{id}")
    public ResponseEntity<ApiResponse<?>> getBaibaoByTacGiaId(
            @PathVariable("id") Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Baibao> pageResult = bbService.getBaibaoByTacGiaId(id, pageable);
        // Page<BaibaoResponseDTO> responsePage = pageResult.map(this::convertToDTO);
        ApiResponse<Page<?>> response = new ApiResponse<>(true, "Fetch bai bao successful",
                pageResult);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/get/baibao/author")
    public ResponseEntity<ApiResponse<?>> getBaiBaoFromToken(
            @RequestBody(required = false) DTOTacGia entity,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size) {
        try {
            if (entity.getToken().isBlank() || entity.getToken() == null) {
                ApiResponse<Page<?>> response = new ApiResponse<>(false, "Lỗi token không hợp lệ", null);
                return ResponseEntity.badRequest().body(response);
            }
        } catch (Exception e) {
            // TODO: handle exception
            ApiResponse<Page<?>> response = new ApiResponse<>(false, "Lỗi token", null);
            return ResponseEntity.badRequest().body(response);
        }
        Taikhoan tk = jwtUtil.getTaikhoanFromToken(entity.getToken());
        if (tk == null) {
            ApiResponse<Page<?>> response = new ApiResponse<>(false, "Lỗi token không hợp lệ", null);
            return ResponseEntity.badRequest().body(response);
        }
        Pageable pageable = PageRequest.of(page, size);
        Page<Baibao> pageResult = bbService.getBaibaoByTacGiaId(tk.getTaikhoan_id(), pageable);

        // Page<BaibaoResponseDTO> responsePage = pageResult.map(this::convertToDTO);
        ApiResponse<Page<?>> response = new ApiResponse<>(true, "Fetch bai bao successful",
                pageResult);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/theloai/{id}")
    public ResponseEntity<ApiResponse<?>> getBaibaoByTheLoaiID(
            @PathVariable("id") Integer id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Baibao> pageResult = bbService.getBaibaoByTheLoaiID(id, pageable);
        // Page<BaibaoResponseDTO> responsePage = pageResult.map(this::convertToDTO);
        ApiResponse<Page<?>> response = new ApiResponse<>(true, "Fetch bai bao successful",
                pageResult);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/date/{postDate}")
    public ResponseEntity<ApiResponse<?>> getBaibaoByNgayDang(
            @PathVariable("postDate") LocalDate postDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Baibao> pageResult = bbService.getBaibaoByNgayDang(postDate, pageable);
        // Page<BaibaoResponseDTO> responsePage = pageResult.map(this::convertToDTO);
        ApiResponse<Page<?>> response = new ApiResponse<>(true, "Fetch bai bao successful",
                pageResult);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/date/{date1}/{date2}")
    public ResponseEntity<ApiResponse<?>> getBaibaiByNgayDangBetween(
            @PathVariable("date1") LocalDate date1,
            @PathVariable("date2") LocalDate date2,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Baibao> pageResult = bbService.getBaibaiByNgayDangBetween(date1, date2, pageable);
        // Page<BaibaoResponseDTO> responsePage = pageResult.map(this::convertToDTO);
        ApiResponse<Page<?>> response = new ApiResponse<>(true, "Fetch bai bao successful",
                pageResult);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse<?>> getBaibaoByTrangThai(
            @PathVariable("status") Integer status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Baibao> pageResult = bbService.getBaibaoByTrangThai(status, pageable);
        // Page<BaibaoResponseDTO> responsePage = pageResult.map(this::convertToDTO);
        ApiResponse<Page<?>> response = new ApiResponse<>(true, "Fetch bai bao successful",
                pageResult);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<?>> deleteBaibao(@PathVariable("id") Integer id) {
        bbService.deleteBaibao(id);
        ApiResponse<?> response = new ApiResponse<>(true, "Delete bai bao successful", null);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<?>> createBaibao(@RequestBody DTOBaiBao entity) {
        // Extract token from request body
        try {
            String token = String.valueOf(entity.getToken());
            System.out.println("Token: " + token);
            Taikhoan tk = jwtUtil.getTaikhoanFromToken(token);
            if (tk == null) {
                ApiResponse<?> response = new ApiResponse<>(false, "Lỗi token không hợp lệ", null);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
            // Convert request body to Baibao object
            try {
                if (entity.getTheloaiId() == null) {
                    ApiResponse<Baibao> response = new ApiResponse<>(false, "Lỗi khi tìm thể loại", null);
                    return ResponseEntity.badRequest().body(response);
                }
                if (entity.getTheloaiId().isBlank()) {
                    ApiResponse<Baibao> response = new ApiResponse<>(false, "Lỗi khi tìm thể loại", null);
                    return ResponseEntity.badRequest().body(response);
                }
            } catch (Exception e) {
                // TODO: handle exception
                ApiResponse<Baibao> response = new ApiResponse<>(false, "Lỗi khi tìm thể loại", null);
                System.out.println("Loi tao bb: " + e.getMessage());
                return ResponseEntity.badRequest().body(response);
            }

            Theloai tl = theloaiRepository.findTheloaiById(Integer.valueOf(entity.getTheloaiId()));
            if (tl == null) {
                ApiResponse<Baibao> response = new ApiResponse<>(false, "Lỗi khi tìm thể loại", null);
                return ResponseEntity.badRequest().body(response);
            }
            Baibao baibao = new Baibao();
            baibao.setTieude(entity.getTieude());
            baibao.setNoidung(entity.getNoidung());
            baibao.setUrl(entity.getUrl());
            baibao.setFile(entity.getFile());
            baibao.setKeyword(entity.getTukhoa());

            baibao.setTheloai(tl);
            baibao.setTaikhoan(tk);

            // Set default values
            baibao.setNgaytao(LocalDate.now());
            baibao.setStatus(0);

            Baibao bb = bbService.saveBaibao(baibao);
            ApiResponse<Baibao> response = new ApiResponse<>(true, "Tạo bài báo thành công", bb);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            // TODO: handle exception
            ApiResponse<Baibao> response = new ApiResponse<>(true, "Lỗi khi tạo bài báo", null);
            System.out.println("Loi tao bb: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }

    }

    // private BaibaoResponseDTO convertToDTO(Baibao baibao) {
    // String tieuDe = null;
    // Integer so = null;
    // Integer tuan = null;

    // List<Danhmucbaibao> danhmucbaibaos = baibao.getDanhmucbaibaos();
    // for (Danhmucbaibao dmbb : danhmucbaibaos) {
    // DanhMuc dm = dmbb.getDanhmuc();
    // if (dm != null) {
    // tieuDe = dm.getTieuDe();
    // so = dm.getSo();
    // tuan = dm.getTuan();
    // break; // Assuming one-to-one relationship for simplicity
    // }
    // }

    // return new BaibaoResponseDTO(baibao, tieuDe, so, tuan);
    // }

}