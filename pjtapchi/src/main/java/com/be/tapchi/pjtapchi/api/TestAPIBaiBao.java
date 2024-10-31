package com.be.tapchi.pjtapchi.api;

import com.be.tapchi.pjtapchi.model.Baibao;
import com.be.tapchi.pjtapchi.model.Binhluan;
import com.be.tapchi.pjtapchi.model.DanhMuc;
import com.be.tapchi.pjtapchi.model.Danhmucbaibao;
import com.be.tapchi.pjtapchi.model.HoaDon;
import com.be.tapchi.pjtapchi.model.HopDong;
import com.be.tapchi.pjtapchi.model.Kiemduyet;
import com.be.tapchi.pjtapchi.model.Taikhoan;
import com.be.tapchi.pjtapchi.model.Theloai;
import com.be.tapchi.pjtapchi.repository.DanhMucBaiBaoRepository;
import com.be.tapchi.pjtapchi.repository.DanhMucRepository;
import com.be.tapchi.pjtapchi.repository.HoaDonRepository;
import com.be.tapchi.pjtapchi.repository.HopDongRepository;
import com.be.tapchi.pjtapchi.repository.KiemduyetRepository;
import com.be.tapchi.pjtapchi.service.BaibaoService;
import com.be.tapchi.pjtapchi.service.BinhluanService;
import com.be.tapchi.pjtapchi.service.HopDongService;
import com.be.tapchi.pjtapchi.service.TheloaiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
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
    @Autowired
    private HoaDonRepository hoaDonRepository;
    @Autowired
    private HopDongRepository hopDongRepository;
    @Autowired
    private DanhMucRepository danhMucRepository;
    @Autowired
    private DanhMucBaiBaoRepository danhMucBaiBaoRepository;
    @Autowired
    private KiemduyetRepository kiemduyetRepository;

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
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, "Request body is missing", null));
        }
        baibao.setTieude("Test");
        baibao.setNoidung("Test");
        baibao.setUrl("Test");
        baibao.setNgaydang(LocalDate.now());
        baibao.setStatus(1);
        bbService.saveBaibao(baibao);
        return ResponseEntity.ok().body(new ApiResponse<>(true, "Create baibao  successful", null));
    }

    @GetMapping("hopdong")
    public ResponseEntity<ApiResponse<List<HopDong>>> getExample2() {
        List<HopDong> list = hopDongRepository.findAll();
        ApiResponse<List<HopDong>> response = new ApiResponse<>(true, "Fetch baibao successful", list);

        if (list.isEmpty()) {
            return ResponseEntity.ok().body(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("baobaotheloai/{id}")
    public ResponseEntity<ApiResponse<List<Baibao>>> getBaibaoByTheLoaiId(@PathVariable Integer id) {
        List<Baibao> list = bbService.getBaibaoByTheLoaiID(id);
        ApiResponse<List<Baibao>> response = new ApiResponse<>(true, "Fetch baibao by the loai successful", list);

        if (list.isEmpty()) {
            return ResponseEntity.ok().body(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("hoadon")
    public ResponseEntity<ApiResponse<List<HoaDon>>> getExample3() {
        List<HoaDon> list = hoaDonRepository.findAll();
        ApiResponse<List<HoaDon>> response = new ApiResponse<>(true, "Fetch baibao successful", list);

        if (list.isEmpty()) {
            return ResponseEntity.ok().body(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("danhmuc")
    public ResponseEntity<ApiResponse<List<DanhMuc>>> getExample4() {
        List<DanhMuc> list = danhMucRepository.findAll();
        ApiResponse<List<DanhMuc>> response = new ApiResponse<>(true, "Fetch baibao successful", list);

        if (list.isEmpty()) {
            return ResponseEntity.ok().body(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("danhmucbaibao")
    public ResponseEntity<ApiResponse<List<Danhmucbaibao>>> getExample5() {
        List<Danhmucbaibao> list = danhMucBaiBaoRepository.findAll();
        ApiResponse<List<Danhmucbaibao>> response = new ApiResponse<>(true, "Fetch baibao successful", list);

        if (list.isEmpty()) {
            return ResponseEntity.ok().body(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("kiemduyet")
    public ResponseEntity<ApiResponse<List<Kiemduyet>>> getExample6() {
        List<Kiemduyet> list = kiemduyetRepository.findAll();
        ApiResponse<List<Kiemduyet>> response = new ApiResponse<>(true, "Fetch baibao successful", list);

        if (list.isEmpty()) {
            return ResponseEntity.ok().body(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

}