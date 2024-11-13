package com.be.tapchi.pjtapchi.controller.hopdong;


import com.be.tapchi.pjtapchi.controller.apiResponse.ApiResponse;
import com.be.tapchi.pjtapchi.model.HopDong;
import com.be.tapchi.pjtapchi.service.HopDongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contract")
public class hopdongController {

    @Autowired
    private HopDongService hopDongService;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<HopDong>> getContract(@PathVariable("id") Long id) {
        HopDong contract = hopDongService.getHopDongById(id);
        ApiResponse<HopDong> response = new ApiResponse<>(true, "Fetch contract successful", contract);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<HopDong>> createContract(@RequestBody HopDong hopDong) {

        HopDong contract = hopDongService.saveHopDong(hopDong);
        ApiResponse<HopDong> response = new ApiResponse<>(true, "Create contract successful", null);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteBaibao(@PathVariable("id") Long id) {
        hopDongService.deleteHopDong(id);
        ApiResponse<Void> response = new ApiResponse<>(true, "Delete contract successful", null);
        return ResponseEntity.ok().body(response);
    }

}
