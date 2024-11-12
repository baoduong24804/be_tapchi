package com.be.tapchi.pjtapchi.controller.danhmuc;

import com.be.tapchi.pjtapchi.controller.apiResponse.ApiResponse;
import com.be.tapchi.pjtapchi.controller.danhmuc.model.Danhmucweek;
import com.be.tapchi.pjtapchi.model.DanhMuc;
import com.be.tapchi.pjtapchi.service.DanhMucService;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/danhmuc")
public class DanhMucController {

    @Autowired
    DanhMucService danhMucService;

    @PostMapping("/get/week")
    public ResponseEntity<ApiResponse<?>> getDanhmucInCurrentWeek(
            @RequestParam(defaultValue = "0") int page, 
            @RequestParam(defaultValue = "10") int size) {
        // TODO: process POST request
            try {
                ApiResponse<?> api = new ApiResponse<>();
                api.setSuccess(true);
                api.setMessage("Thành công lấy dữ liệu danh mục theo tuần");
                Map<String,Object> map = new HashMap<>();
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

}