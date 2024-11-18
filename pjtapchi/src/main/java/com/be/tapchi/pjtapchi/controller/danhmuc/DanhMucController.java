package com.be.tapchi.pjtapchi.controller.danhmuc;


import com.be.tapchi.pjtapchi.controller.apiResponse.ApiResponse;
import com.be.tapchi.pjtapchi.model.Baibao;
import com.be.tapchi.pjtapchi.model.DanhMuc;
import com.be.tapchi.pjtapchi.model.Danhmucbaibao;
import com.be.tapchi.pjtapchi.repository.DanhMucBaiBaoRepository;
import com.be.tapchi.pjtapchi.repository.DanhMucRepository;
import com.be.tapchi.pjtapchi.repository.TaiKhoanRepository;
import com.be.tapchi.pjtapchi.service.DanhMucService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.HashMap;
import java.util.Map;


@CrossOrigin(origins = "*")

@RestController
@RequestMapping("/api/danhmuc")
public class DanhMucController {

    @Autowired
    DanhMucService danhMucService;

    @Autowired
    DanhMucBaiBaoRepository danhMucBaiBaoRepository;

    @Autowired
    DanhMucRepository danhMucRepository;

    @Autowired
    TaiKhoanRepository taiKhoanRepository;

    @PostMapping("/get/week")
    public ResponseEntity<ApiResponse<?>> getDanhmucInCurrentWeek(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        // TODO: process POST requestS
        try {
            ApiResponse<?> api = new ApiResponse<>();
            api.setSuccess(true);
            api.setMessage("Thành công lấy dữ liệu danh mục theo tuần");
            Map<String, Object> map = new HashMap<>();
            Page<DanhMuc> dm = danhMucService.getDanhmucInCurrentWeek(page, size);
            map.put("data", dm.getContent());
            map.put("totalPage", String.valueOf(dm.getTotalPages()));
            map.put("pageNumber", String.valueOf(dm.getNumber()));
            map.put("pageSize", String.valueOf(dm.getSize()));
            map.put("totalElements", String.valueOf(dm.getTotalElements()));
            api.setData(map);
            return ResponseEntity.ok().body(api);
        } catch (Exception e) {
            // TODO: handle exception
        }


        return ResponseEntity.badRequest().body(null);
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<Page<DanhMuc>>> getAllDanhMuc(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<DanhMuc> danhMucPage = danhMucService.getAllDanhMuc(pageable);
            ApiResponse<Page<DanhMuc>> response = new ApiResponse<>(true, "Danh sách danh mục", danhMucPage);

            if (danhMucPage.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            } else {
                return ResponseEntity.ok().body(response);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Đã xảy ra lỗi khi lấy danh mục.", null));
        }
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<DanhMuc>> createDanhMuc(@RequestBody DanhMuc danhMuc) {
        try {
            if (danhMuc.getStatus() == null) {
                danhMuc.setStatus(0);
            }
            if (danhMuc.getNgayTao() == null) {
                danhMuc.setNgayTao(new Date());
            }
            DanhMuc dm = danhMucService.saveDanhMuc(danhMuc);
            ApiResponse<DanhMuc> response = new ApiResponse<>(true, "Create danh muc successful", dm);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Đã xảy ra lỗi khi tạo danh mục.", null));
        }
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<String>> deleteDanhMuc(@PathVariable("id") Long id) {
        try {
            DanhMuc danhMuc = danhMucService.getDanhMucById(id);
            if (danhMuc == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(false, "Danh mục không tồn tại", null));
            }
            danhMucService.deleteDanhMuc(id);
            return ResponseEntity.ok().body(new ApiResponse<>(true, "Delete danh muc successful", "Đã xóa danh mục với id = " + id));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Đã xảy ra lỗi khi xóa danh mục.", null));
        }
    }

    @GetMapping("/findbyID/{id}")
    public ResponseEntity<ApiResponse<DanhMuc>> findById(@PathVariable("id") Long id) {
        try {
            DanhMuc dm = danhMucService.getDanhMucById(id);
            if (dm == null) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(false, "Danh mục không tồn tại", null));
            }
            return ResponseEntity.ok().body(new ApiResponse<>(true, "Find danh muc by ID successful", dm));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Đã xảy ra lỗi khi tìm kiếm danh mục.", null));
        }
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<ApiResponse<DanhMuc>> updateDanhMuc(@PathVariable("id") Long id,
                                                              @RequestBody DanhMuc newDanhMuc) {
        try {
            DanhMuc updatedDanhMuc = danhMucService.updateDanhMuc(id, newDanhMuc);
            if (updatedDanhMuc != null) {
                return ResponseEntity.ok().body(new ApiResponse<>(true, "Update danh muc successful", updatedDanhMuc));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(false, "Danh muc not found", null));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Đã xảy ra lỗi khi cập nhật danh mục.", null));
        }
    }


    // tìm baibao where danhmucid = {danhmucid}
    @GetMapping("/{danhmucId}/listBb")
    public ResponseEntity<ApiResponse<?>> getBaibaosByDanhMuc(@PathVariable Long danhmucId) {
        try {
            // tim ban ghi danh muc
            DanhMuc dmBaibao = danhMucRepository.findById(danhmucId).orElse(null);
            if (dmBaibao == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(false, "Danh mục không tồn tại", null));
            }
            List<Danhmucbaibao> listDm = dmBaibao.getDanhmucbaibaos();
            List<Baibao> baibaos = new ArrayList<>();

            for (Danhmucbaibao danhmucbaibao : listDm) {
                try {

                    Baibao baibao = danhmucbaibao.getBaibao();
                    // kiemtra taikhoanid co ton tai
                    if (baibao.getTaikhoan() == null || !taiKhoanRepository.existsById(baibao.getTaikhoan().getTaikhoan_id())) {
                        System.out.println("<<< taikhoanid khong ton tai : " + baibao.getTaikhoan().getTaikhoan_id() + " >>>");
                        continue;
                    }
                } catch (Exception e) {
                    continue;
                }
                baibaos.add(danhmucbaibao.getBaibao());
            }
            List<Baibao> baibaoList = new ArrayList<>();
            for (Baibao baibao : baibaos) {
                Baibao bb = new Baibao();
                bb.setId(baibao.getId());
                bb.setStatus(baibao.getStatus());
                bb.setTaikhoan(baibao.getTaikhoan());
                bb.setNoidung(baibao.getNoidung());
                bb.setNgaydang(baibao.getNgaydang());
                bb.setTieude(baibao.getTieude());
                bb.setUrl(baibao.getUrl());
                baibaoList.add(bb);
            }
            if (baibaoList.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(false, "Không có bài báo hợp lệ trong danh mục này.", null));
            }
            Map<String, Object> map = new HashMap<>();
            map.put("baibaoList", baibaoList);
            return ResponseEntity.ok()
                    .body(new ApiResponse<>(true, "Danh sách bài báo", map));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Đã xảy ra lỗi khi xử lý yêu cầu.", null));
        DanhMuc updatedDanhMuc = danhMucService.updateDanhMuc(id, newDanhMuc);
        if (updatedDanhMuc != null) {
            return ResponseEntity.ok().body(new ApiResponse<>(true, "Update danh muc successful", updatedDanhMuc));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Danh muc not found", null));
        }
    }

}