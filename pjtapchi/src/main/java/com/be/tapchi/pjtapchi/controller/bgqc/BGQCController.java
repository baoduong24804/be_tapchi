package com.be.tapchi.pjtapchi.controller.bgqc;

import com.be.tapchi.pjtapchi.controller.apiResponse.ApiResponse;
import com.be.tapchi.pjtapchi.model.BangGiaQC;
import com.be.tapchi.pjtapchi.service.BangGiaQCService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/bgqc")
public class BGQCController {

    @Autowired
    private BangGiaQCService bgqcService;

    // Get all BangGiaQC
    @GetMapping("/all")
    public ApiResponse<List<BangGiaQC>> getAllBangGiaQC() {
        List<BangGiaQC> bangGiaQCList = bgqcService.findAll();
        return new ApiResponse<>(true, "Fetched all BangGiaQC successfully", bangGiaQCList);
    }
}
