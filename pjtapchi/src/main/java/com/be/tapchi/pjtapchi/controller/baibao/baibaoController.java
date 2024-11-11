package com.be.tapchi.pjtapchi.controller.baibao;

import com.be.tapchi.pjtapchi.api.ApiResponse;
import com.be.tapchi.pjtapchi.model.Baibao;
import com.be.tapchi.pjtapchi.service.BaibaoService;
import com.be.tapchi.pjtapchi.service.BinhluanService;
import com.be.tapchi.pjtapchi.service.TheloaiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("api/baibao")
public class baibaoController {

    @Autowired
    BinhluanService BinhluanService;

    @Autowired
    private BaibaoService bbService;

    @Autowired
    private TheloaiService theloaiService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<Page<Baibao>>> getAllBaibao(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Baibao> pageResult = bbService.findAllBaibao(pageable);
        ApiResponse<Page<Baibao>> response = new ApiResponse<>(true, "Fetch bai bao successful", pageResult);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Baibao>> getExample(@PathVariable("id") Integer id) {
        Baibao bb = bbService.getBaibaoById(id);
        ApiResponse<Baibao> response = new ApiResponse<>(true, "Fetch bai bao successful", bb);
        if (bb != null) {
            return ResponseEntity.ok().body(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("/author/{id}")
    public ResponseEntity<ApiResponse<Page<Baibao>>> getBaibaoByTacGiaId(
            @PathVariable("id") Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Baibao> pageResult = bbService.getBaibaoByTacGiaId(id, pageable);
        ApiResponse<Page<Baibao>> response = new ApiResponse<>(true, "Fetch bai bao successful", pageResult);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/theloai/{id}")
    public ResponseEntity<ApiResponse<Page<Baibao>>> getBaibaoByTheLoaiID(
            @PathVariable("id") Integer id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Baibao> pageResult = bbService.getBaibaoByTheLoaiID(id, pageable);
        ApiResponse<Page<Baibao>> response = new ApiResponse<>(true, "Fetch bai bao successful", pageResult);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/date/{postDate}")
    public ResponseEntity<ApiResponse<Page<Baibao>>> getBaibaoByNgayDang(
            @PathVariable("postDate") LocalDate postDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Baibao> pageResult = bbService.getBaibaoByNgayDang(postDate, pageable);
        ApiResponse<Page<Baibao>> response = new ApiResponse<>(true, "Fetch bai bao successful", pageResult);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/date/{date1}/{date2}")
    public ResponseEntity<ApiResponse<Page<Baibao>>> getBaibaiByNgayDangBetween(
            @PathVariable("date1") LocalDate date1,
            @PathVariable("date2") LocalDate date2,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Baibao> pageResult = bbService.getBaibaiByNgayDangBetween(date1, date2, pageable);
        ApiResponse<Page<Baibao>> response = new ApiResponse<>(true, "Fetch bai bao successful", pageResult);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse<Page<Baibao>>> getBaibaoByTrangThai(
            @PathVariable("status") Integer status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Baibao> pageResult = bbService.getBaibaoByTrangThai(status, pageable);
        ApiResponse<Page<Baibao>> response = new ApiResponse<>(true, "Fetch bai bao successful", pageResult);
        return ResponseEntity.ok().body(response);
    }
}