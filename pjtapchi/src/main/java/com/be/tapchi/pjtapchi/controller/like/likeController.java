package com.be.tapchi.pjtapchi.controller.like;


import com.be.tapchi.pjtapchi.controller.apiResponse.ApiResponse;
import com.be.tapchi.pjtapchi.model.Thich;
import com.be.tapchi.pjtapchi.service.ThichService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/like")
public class likeController {

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
    public ResponseEntity<ApiResponse<Thich>> saveThich(Thich thich) {
        Thich savedThich = thichService.save(thich);
        return ResponseEntity.ok().body(new ApiResponse<>(true, "Save thich successful", savedThich));
    }

    @PostMapping("/unlike")
    public ResponseEntity<ApiResponse<Void>> deleteThich(Long id) {
        thichService.deleteById(id);
        return ResponseEntity.ok().body(new ApiResponse<>(true, "Delete thich successful", null));
    }
}
