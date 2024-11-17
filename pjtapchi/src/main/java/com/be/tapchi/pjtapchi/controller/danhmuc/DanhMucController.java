package com.be.tapchi.pjtapchi.controller.danhmuc;


import com.be.tapchi.pjtapchi.controller.apiResponse.ApiResponse;
import com.be.tapchi.pjtapchi.model.Baibao;
import com.be.tapchi.pjtapchi.model.DanhMuc;
import com.be.tapchi.pjtapchi.model.Danhmucbaibao;
import com.be.tapchi.pjtapchi.repository.DanhMucBaiBaoRepository;
import com.be.tapchi.pjtapchi.repository.DanhMucRepository;
import com.be.tapchi.pjtapchi.service.DanhMucService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;


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

    @PostMapping("/get/week")
    public ResponseEntity<ApiResponse<?>> getDanhmucInCurrentWeek(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        // TODO: process POST request
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
        Pageable pageable = PageRequest.of(page, size);
        Page<DanhMuc> danhMucPage = danhMucService.getAllDanhMuc(pageable);
        ApiResponse<Page<DanhMuc>> response = new ApiResponse<>(true, "Danh sách danh mục", danhMucPage);

        if (danhMucPage.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } else {
            return ResponseEntity.ok().body(response);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<DanhMuc>> createDanhMuc(@RequestBody DanhMuc danhMuc) {
        if (danhMuc.getStatus() == null) {
            danhMuc.setStatus(0);
        }
        if (danhMuc.getNgayTao() == null) {
            danhMuc.setNgayTao(new Date());
        }
        DanhMuc dm = danhMucService.saveDanhMuc(danhMuc);
        ApiResponse<DanhMuc> response = new ApiResponse<>(true, "Create danh muc successful", dm);
        return ResponseEntity.ok().body(response);
    }


    @GetMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<String>> deleteDanhMuc(@PathVariable("id") Long id) {
        DanhMuc danhMuc = danhMucService.getDanhMucById(id);
        if (danhMuc == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Danh mục không tồn tại", null));
        }
        danhMucService.deleteDanhMuc(id);
        return ResponseEntity.ok().body(new ApiResponse<>(true, "Delete danh muc successful", "Đã xóa danh mục với id = " + id));
    }

    @GetMapping("/findbyID/{id}")
    public ResponseEntity<ApiResponse<DanhMuc>> findById(@PathVariable("id") Long id) {
        DanhMuc dm = danhMucService.getDanhMucById(id);
        if (dm == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Danh mục không tồn tại", null));
        }
        return ResponseEntity.ok().body(new ApiResponse<>(true, "Find danh muc by ID successful", dm));
    }


    @PostMapping("/update/{id}")
    public ResponseEntity<ApiResponse<DanhMuc>> updateDanhMuc(@PathVariable("id") Long id,
                                                              @RequestBody DanhMuc newDanhMuc) {
        DanhMuc updatedDanhMuc = danhMucService.updateDanhMuc(id, newDanhMuc);
        if (updatedDanhMuc != null) {
            return ResponseEntity.ok().body(new ApiResponse<>(true, "Update danh muc successful", updatedDanhMuc));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Danh muc not found", null));
        }
    }

    // tìm baibao where danhmucid = {danhmucid}
    @GetMapping("/{danhmucId}/listBb")
    public ResponseEntity<ApiResponse<?>> getBaibaosByDanhMuc(@PathVariable Long danhmucId) {
        try {
            DanhMuc dmBaibao = danhMucRepository.findById(danhmucId).orElse(null);
            System.out.println("1");
            List<Danhmucbaibao> listDm = dmBaibao.getDanhmucbaibaos();
            System.out.println("2");
            List<Baibao> baibaos = new ArrayList<>();
            System.out.println("3");
            for (Danhmucbaibao danhmucbaibao : listDm) {
                try {
                    if (danhmucbaibao.getBaibao().getTaikhoan() == null) {
                        continue;
                    }
                } catch (Exception e) {
                    continue;
                }
                baibaos.add(danhmucbaibao.getBaibao());

            }
            System.out.println("4");
            Map<String, Object> map = new HashMap<>();
            List<Baibao> baibaoList = new ArrayList<>();
            for (Baibao baibao : baibaos) {
                try {
                    if (baibao.getTaikhoan() == null) {
                        continue;
                    }
                    if (baibao.getBinhluans() == null) {
                        continue;
                    }
                    if (baibao.getThichs() == null) {
                        continue;
                    }
                } catch (Exception e) {
                    continue;
                }
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
            map.put("baibaoList", baibaoList);
            if (baibaos.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(false, "Không có bài báo nào trong danh mục này", null));
            }
            return ResponseEntity.ok()
                    .body(new ApiResponse<>(true, "Danh sach bai bao ", map));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}