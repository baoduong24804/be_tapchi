package com.be.tapchi.pjtapchi.controller.like;


import com.be.tapchi.pjtapchi.controller.apiResponse.ApiResponse;
import com.be.tapchi.pjtapchi.jwt.JwtUtil;
import com.be.tapchi.pjtapchi.model.Baibao;
import com.be.tapchi.pjtapchi.model.Taikhoan;
import com.be.tapchi.pjtapchi.model.Thich;
import com.be.tapchi.pjtapchi.service.ThichService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/like")
public class likeController {

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    private ThichService thichService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<Thich>>> getExample() {
        List<Thich> list = thichService.findAll();
        ApiResponse<List<Thich>> response = new ApiResponse<>(true, "Fetch thich successful", list);
        if (!list.isEmpty()) {
            return ResponseEntity.ok().body(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "No thich found", null));
        }
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Thich>> saveThich(@RequestBody Map<String, Object> requestBody) {
        Taikhoan taikhoan = jwtUtil.getTaikhoanFromToken(requestBody.get("token").toString());

        Thich thich = new Thich();
        thich.setTaikhoan(taikhoan);
        Baibao baibao = new Baibao();
        baibao.setId(Integer.parseInt(requestBody.get("baibao_id").toString()));
        thich.setBaibao(baibao);
        Thich newThich = thichService.save(thich);
        return ResponseEntity.ok().body(new ApiResponse<>(true, "Create thich successful", newThich));
    }

    @PostMapping("/unlike")
    public ResponseEntity<ApiResponse<Void>> deleteThich(Long id) {
        thichService.deleteById(id);
        return ResponseEntity.ok().body(new ApiResponse<>(true, "Delete thich successful", null));
    }
}
