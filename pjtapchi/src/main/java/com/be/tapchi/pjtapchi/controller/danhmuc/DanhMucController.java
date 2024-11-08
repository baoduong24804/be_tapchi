package com.be.tapchi.pjtapchi.controller.danhmuc;

import com.be.tapchi.pjtapchi.api.ApiResponse;
import com.be.tapchi.pjtapchi.model.DanhMuc;
import com.be.tapchi.pjtapchi.service.DanhMucService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<ApiResponse<List<DanhMuc>>> getAllDanhMuc() {
        List<DanhMuc> danhMucList = danhMucService.getAllDanhMuc();
        ApiResponse<List<DanhMuc>> response = new ApiResponse<>(true, "Danh sách danh mục", danhMucList);

        if (danhMucList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } else {
            return ResponseEntity.ok().body(response);
        }
    }

    @PostMapping("/create/{tendanhmuc}")
    public ResponseEntity<ApiResponse<DanhMuc>> createDanhMuc(@PathVariable("tendanhmuc") String tendanhmuc) {
        DanhMuc danhMuc = new DanhMuc();
        danhMuc.setTieuDe(tendanhmuc);

        DanhMuc savedDanhMuc = danhMucService.saveDanhMuc(danhMuc);
        ApiResponse<DanhMuc> response = new ApiResponse<>(true, "Save danh muc successful", savedDanhMuc);
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

}