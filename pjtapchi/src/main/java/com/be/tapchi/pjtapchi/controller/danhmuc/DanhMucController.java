package com.be.tapchi.pjtapchi.controller.danhmuc;

import com.be.tapchi.pjtapchi.api.ApiResponse;
import com.be.tapchi.pjtapchi.model.Baibao;
import com.be.tapchi.pjtapchi.model.DanhMuc;
import com.be.tapchi.pjtapchi.service.DanhMucService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/danhmuc")
public class DanhMucController {

    @Autowired
    DanhMucService danhMucService;


    @GetMapping("/all")
    public ResponseEntity<ApiResponse<Page<DanhMuc>>> getAllDanhMuc(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size
    ) {
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
    public ResponseEntity<ApiResponse<DanhMuc>> createBaibao(@RequestBody DanhMuc danhMuc) {
        DanhMuc dm = danhMucService.saveDanhMuc(danhMuc);
        ApiResponse<DanhMuc> response = new ApiResponse<>(true, "Create danh muc successful", dm);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteDanhMuc(@PathVariable("id") Long id) {
        danhMucService.deleteDanhMuc(id);
        return ResponseEntity.ok().body(new ApiResponse<>(true, "Delete danh muc successful", null));
    }

    @GetMapping("/findbyID/{id}")
    public ResponseEntity<ApiResponse<DanhMuc>> findById(@PathVariable("id") Long id) {
        danhMucService.getDanhMucById(id);
        return ResponseEntity.ok().body(new ApiResponse<>(true, "Find danh muc by ID successful", null));

    }

    @PostMapping("/update/{id}")
    public ResponseEntity<ApiResponse<DanhMuc>> updateDanhMuc(@PathVariable("id") Long id, @RequestBody DanhMuc newDanhMuc) {
        DanhMuc updatedDanhMuc = danhMucService.updateDanhMuc(id, newDanhMuc);
        if (updatedDanhMuc != null) {
            return ResponseEntity.ok().body(new ApiResponse<>(true, "Update danh muc successful", updatedDanhMuc));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, "Danh muc not found", null));
        }
    }

    @GetMapping("/{danhmucId}/listBb")
    public ResponseEntity<ApiResponse<List<Baibao>>> getBaibaosByDanhMuc(@PathVariable Long danhmucId) {
        List<Baibao> baibaos = danhMucService.getBbByIdDanhMuc(danhmucId);

        if (baibaos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Không có bài báo nào trong danh mục này", null));
        }

        return ResponseEntity.ok(new ApiResponse<>(true, "Danh sách bài báo trong danh mục", baibaos));
    }

}