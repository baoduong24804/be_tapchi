package com.be.tapchi.pjtapchi.api;

import com.be.tapchi.pjtapchi.model.Baibao;
import com.be.tapchi.pjtapchi.model.Binhluan;
import com.be.tapchi.pjtapchi.model.Theloai;
import com.be.tapchi.pjtapchi.service.BaibaoService;
import com.be.tapchi.pjtapchi.service.BinhluanService;
import com.be.tapchi.pjtapchi.service.TheloaiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api")
public class TestAPIBaiBao {
    @Autowired
    BinhluanService BinhluanService;
    @Autowired
    private BaibaoService bbService;
    @Autowired
    private TheloaiService theloaiService;

    @GetMapping("baibao")
    public ResponseEntity<ApiResponse<List<Baibao>>> getExample() {
        List<Baibao> list = bbService.getAllBaibaos();
        ApiResponse<List<Baibao>> response = new ApiResponse<>(true, "Fetch baibao successful", list);

        if (list.isEmpty()) {
            return ResponseEntity.ok().body(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("theloai")
    public ResponseEntity<ApiResponse<List<Theloai>>> getExamples() {
        List<Theloai> list = theloaiService.getAllTheloais();
        ApiResponse<List<Theloai>> response = new ApiResponse<>(true, "Fetch the loai successful", list);

        if (list.isEmpty()) {
            return ResponseEntity.ok().body(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("binhluan")
    public ResponseEntity<ApiResponse<List<Binhluan>>> getExample1() {
        List<Binhluan> list = BinhluanService.getAllBinhluans();
        ApiResponse<List<Binhluan>> response = new ApiResponse<>(true, "Fetch binhluan successful", list);

        if (list.isEmpty()) {
            return ResponseEntity.ok().body(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    // Create a new baibao
    @PostMapping("baibao")
    public ResponseEntity<ApiResponse<Object>> CreateBaibao(@RequestBody @Validated Baibao baibao) {
        if (baibao == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(false, "Request body is missing", null));
        }
        baibao.setTieude("Test");
        baibao.setNoidung("Test");
        baibao.setUrl("Test");
        baibao.setNgaydang(LocalDate.now());
        baibao.setStatus(1);
        bbService.saveBaibao(baibao);
        return ResponseEntity.ok().body(new ApiResponse<>(true, "Create baibao successful", null));
    }

    // Create a new theloai
    @PostMapping("taotheloai")
    public ResponseEntity<ApiResponse<Object>> CreateTheloai(@RequestBody @Validated Theloai theloai) {
        if (theloai == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(false, "Request body is missing", null));
        }
        theloai.setTenloai("Test");
        theloaiService.saveTheloai(theloai);
        return ResponseEntity.ok().body(new ApiResponse<>(true, "Create theloai successful", null));
    }

    // Create a new binhluan
    @PostMapping("binhluan")
    public ResponseEntity<ApiResponse<Object>> CreateBinhluan(@RequestBody @Validated Binhluan binhluan) {
        if (binhluan == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(false, "Request body is missing", null));
        }
        binhluan.setNoidung("Test");
        binhluan.setStatus(1);
        BinhluanService.saveBinhluan(binhluan);
        return ResponseEntity.ok().body(new ApiResponse<>(true, "Create binhluan successful", null));
    }
    
}