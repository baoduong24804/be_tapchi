package com.be.tapchi.pjtapchi.controller.baibao;

import com.be.tapchi.pjtapchi.api.ApiResponse;
import com.be.tapchi.pjtapchi.model.Baibao;
import com.be.tapchi.pjtapchi.repository.*;
import com.be.tapchi.pjtapchi.service.BaibaoService;
import com.be.tapchi.pjtapchi.service.BinhluanService;
import com.be.tapchi.pjtapchi.service.TheloaiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

/**
 * Controller cho các API liên quan đến Baibao.
 */
@RestController
public class baibaoController {

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

    /**
     * Lấy tất cả các Baibao.
     *
     * @return ResponseEntity chứa danh sách các Baibao.
     */
    @GetMapping("baibao")
    public ResponseEntity<ApiResponse<List<Baibao>>> getExample() {
        List<Baibao> list = bbService.getAllBaibaos();
        ApiResponse<List<Baibao>> response = new ApiResponse<>(true, "Fetch bai bao successful", list);
        if (!list.isEmpty()) {
            return ResponseEntity.ok().body(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    /**
     * Lấy Baibao theo ID.
     *
     * @param id ID của Baibao.
     * @return ResponseEntity chứa Baibao.
     */
    @GetMapping("baibao/{id}")
    public ResponseEntity<ApiResponse<Baibao>> getExample(@PathVariable("id") Integer id) {
        Baibao bb = bbService.getBaibaoById(id);
        ApiResponse<Baibao> response = new ApiResponse<>(true, "Fetch bai bao successful", bb);
        if (bb != null) {
            return ResponseEntity.ok().body(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    /**
     * Lấy danh sách Baibao theo ID tác giả.
     *
     * @param id ID của tác giả.
     * @return ResponseEntity chứa danh sách các Baibao.
     */
    @GetMapping("baibao/tacgia/{id}")
    public ResponseEntity<ApiResponse<List<Baibao>>> getBaibaoByTacGiaId(@PathVariable("id") Long id) {
        List<Baibao> list = bbService.getBaibaoByTacGiaId(id);
        ApiResponse<List<Baibao>> response = new ApiResponse<>(true, "Fetch bai bao successful", list);
        if (!list.isEmpty()) {
            return ResponseEntity.ok().body(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    /**
     * Lấy danh sách Baibao theo ID thể loại.
     *
     * @param id ID của thể loại.
     * @return ResponseEntity chứa danh sách các Baibao.
     */
    @GetMapping("baibao/theloai/{id}")
    public ResponseEntity<ApiResponse<List<Baibao>>> getBaibaoByTheLoaiID(@PathVariable("id") Integer id) {
        List<Baibao> list = bbService.getBaibaoByTheLoaiID(id);
        ApiResponse<List<Baibao>> response = new ApiResponse<>(true, "Fetch bai bao successful", list);
        if (!list.isEmpty()) {
            return ResponseEntity.ok().body(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    /**
     * Lấy danh sách Baibao theo ngày đăng.
     *
     * @param postDate Ngày đăng của Baibao.
     * @return ResponseEntity chứa danh sách các Baibao.
     */
    @GetMapping("baibao/date/{postDate}")
    public ResponseEntity<ApiResponse<List<Baibao>>> getBaibaoByNgayDang(@PathVariable("postDate") LocalDate postDate) {
        List<Baibao> list = bbService.getBaibaoByNgayDang(postDate);
        ApiResponse<List<Baibao>> response = new ApiResponse<>(true, "Fetch bai bao successful", list);
        if (!list.isEmpty()) {
            return ResponseEntity.ok().body(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    /**
     * Lấy danh sách Baibao theo khoảng ngày đăng.
     *
     * @param date1 Ngày bắt đầu.
     * @param date2 Ngày kết thúc.
     * @return ResponseEntity chứa danh sách các Baibao.
     */
    @GetMapping("baibao/date/{date1}/{date2}")
    public ResponseEntity<ApiResponse<List<Baibao>>> getBaibaiByNgayDangBetween(@PathVariable("date1") LocalDate date1,
            @PathVariable("date2") LocalDate date2) {
        List<Baibao> list = bbService.getBaibaiByNgayDangBetween(date1, date2);
        ApiResponse<List<Baibao>> response = new ApiResponse<>(true, "Fetch bai bao successful", list);
        if (!list.isEmpty()) {
            return ResponseEntity.ok().body(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    /**
     * Lấy danh sách Baibao theo trạng thái.
     *
     * @param status Trạng thái của Baibao.
     * @return ResponseEntity chứa danh sách các Baibao.
     */
    @GetMapping("baibao/status/{status}")
    public ResponseEntity<ApiResponse<List<Baibao>>> getBaibaoByTrangThai(@PathVariable("status") Integer status) {
        List<Baibao> list = bbService.getBaibaoByTrangThai(status);
        ApiResponse<List<Baibao>> response = new ApiResponse<>(true, "Fetch bai bao successful", list);
        if (!list.isEmpty()) {
            return ResponseEntity.ok().body(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    /**
     * Lưu Baibao.
     *
     * @param baibao Đối tượng Baibao cần lưu.
     * @return ResponseEntity chứa Baibao đã lưu.
     * @implNote: Waiting for the Frontend to be able to get the data from the form
     *            and send it to the backend.
     */
    @PostMapping("baibao/save")
    public ResponseEntity<ApiResponse<Baibao>> saveBaibao(Baibao baibao) {
        Baibao bb = bbService.saveBaibao(baibao);
        ApiResponse<Baibao> response = new ApiResponse<>(true, "Fetch bai bao successful", bb);
        if (bb != null) {
            return ResponseEntity.ok().body(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    /**
     * Xóa Baibao theo ID.
     *
     * @param id ID của Baibao cần xóa.
     * @return ResponseEntity chứa thông báo xóa thành công.
     */
    @PostMapping("baibao/delete/{id}")
    public ResponseEntity<ApiResponse<String>> deleteBaibao(@PathVariable("id") Integer id) {
        bbService.deleteBaibao(id);
        ApiResponse<String> response = new ApiResponse<>(true, "Delete bai bao successful",
                "Delete bai bao successful");
        return ResponseEntity.ok().body(response);
    }

    /**
     * Cập nhật Baibao.
     *
     * @param baibao Đối tượng Baibao cần cập nhật.
     * @return ResponseEntity chứa Baibao đã cập nhật.
     */
    @PostMapping("baibao/update")
    public ResponseEntity<ApiResponse<Baibao>> updateBaibao(Baibao baibao) {
        Baibao bb = bbService.saveBaibao(baibao);
        ApiResponse<Baibao> response = new ApiResponse<>(true, "Update bai bao successful", bb);
        if (bb != null) {
            return ResponseEntity.ok().body(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}
