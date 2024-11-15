package com.be.tapchi.pjtapchi.controller.kiemduyet;

import com.be.tapchi.pjtapchi.controller.apiResponse.ApiResponse;
import com.be.tapchi.pjtapchi.model.Kiemduyet;
import com.be.tapchi.pjtapchi.service.KiemduyetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/censor")
public class kiemduyetController {

    @Autowired
    private KiemduyetService kiemDuyetService;

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
    public ResponseEntity<ApiResponse<Kiemduyet>> createKiemDuyet(@RequestBody Kiemduyet kiemDuyet) {
        Kiemduyet kiemduyet = kiemDuyetService.saveKiemduyet(kiemDuyet);
        ApiResponse<Kiemduyet> response = new ApiResponse<>(true, "Create kiem duyet successful", kiemduyet);
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
