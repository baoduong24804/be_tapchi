package com.be.tapchi.pjtapchi.controller.danhmuc;

import com.be.tapchi.pjtapchi.api.ApiResponse;
import com.be.tapchi.pjtapchi.model.DanhMuc;
import com.be.tapchi.pjtapchi.service.DanhMucService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/danhmuc")
public class DanhMucController {

    @Autowired
    DanhMucService danhMucService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<DanhMuc>>> getAllDanhMuc() {
        List<DanhMuc> danhMucList = danhMucService.getAllDanhMuc();
        ApiResponse<List<DanhMuc>> response = new ApiResponse<>(true, "Danh sách danh mục", danhMucList);

        if (danhMucList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } else {
            return ResponseEntity.ok().body(response);
        }
    }
}