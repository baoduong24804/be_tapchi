package com.be.tapchi.pjtapchi.controller.comments;

import com.be.tapchi.pjtapchi.controller.apiResponse.ApiResponse;
import com.be.tapchi.pjtapchi.model.Binhluan;
import com.be.tapchi.pjtapchi.service.BinhluanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller cho các API liên quan đến Binhluan.
 */
@RestController
@RequestMapping("api/binhluan")
public class commentsController {
    @Autowired
    private BinhluanService binhluanService;

    /**
     * Lấy tất cả các Binhluan.
     *
     * @return ResponseEntity chứa danh sách các Binhluan.
     */

    @GetMapping("all")
    public ResponseEntity<ApiResponse<List<Binhluan>>> getExample() {
        List<Binhluan> list = binhluanService.getAllBinhluans();
        ApiResponse<List<Binhluan>> response = new ApiResponse<>(true, "Fetch binh luan successful", list);
        if (!list.isEmpty()) {
            return ResponseEntity.ok().body(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "No binh luan found", null));
        }
    }

    @PostMapping("save")
    public ResponseEntity<ApiResponse<Binhluan>> saveBinhluan(@RequestBody Binhluan binhluan) {
        Binhluan savedBinhluan = binhluanService.saveBinhluan(binhluan);
        return ResponseEntity.ok().body(new ApiResponse<>(true, "Save binh luan successful", savedBinhluan));
    }

    @PutMapping("update")
    public ResponseEntity<ApiResponse<Binhluan>> updateBinhluan(@RequestBody Binhluan binhluan) {
        Binhluan updatedBinhluan = binhluanService.updateBinhluan(binhluan);
        return ResponseEntity.ok().body(new ApiResponse<>(true, "Update binh luan successful", updatedBinhluan));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteBinhluan(@PathVariable("id") Long id) {
        binhluanService.deleteBinhluan(id);
        return ResponseEntity.ok().body(new ApiResponse<>(true, "Delete binh luan successful", null));
    }
}
