package com.be.tapchi.pjtapchi.controller.baibao;

import com.be.tapchi.pjtapchi.controller.apiResponse.ApiResponse;
import com.be.tapchi.pjtapchi.dto.BaibaoResponseDTO;
import com.be.tapchi.pjtapchi.model.Baibao;
import com.be.tapchi.pjtapchi.model.DanhMuc;
import com.be.tapchi.pjtapchi.model.Danhmucbaibao;
import com.be.tapchi.pjtapchi.service.BaibaoService;
import com.be.tapchi.pjtapchi.service.BinhluanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/baibao")
public class baibaoController {

    @Autowired
    BinhluanService BinhluanService;

    @Autowired
    private BaibaoService bbService;


    @GetMapping("/all")
    public ResponseEntity<ApiResponse<Page<BaibaoResponseDTO>>> getAllBaibao(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Baibao> pageResult = bbService.findAllBaibao(pageable);
        Page<BaibaoResponseDTO> responsePage = pageResult.map(this::convertToDTO);
        ApiResponse<Page<BaibaoResponseDTO>> response = new ApiResponse<>(true, "Fetch bai bao successful", responsePage);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BaibaoResponseDTO>> getExample(@PathVariable("id") Integer id) {
        Baibao bb = bbService.getBaibaoById(id);
        if (bb != null) {
            BaibaoResponseDTO responseDTO = convertToDTO(bb);
            ApiResponse<BaibaoResponseDTO> response = new ApiResponse<>(true, "Fetch bai bao successful", responseDTO);
            return ResponseEntity.ok().body(response);
        } else {
            ApiResponse<BaibaoResponseDTO> response = new ApiResponse<>(true, "Fetch bai bao successful", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("/author/{id}")
    public ResponseEntity<ApiResponse<Page<BaibaoResponseDTO>>> getBaibaoByTacGiaId(
            @PathVariable("id") Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Baibao> pageResult = bbService.getBaibaoByTacGiaId(id, pageable);
        Page<BaibaoResponseDTO> responsePage = pageResult.map(this::convertToDTO);
        ApiResponse<Page<BaibaoResponseDTO>> response = new ApiResponse<>(true, "Fetch bai bao successful", responsePage);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/theloai/{id}")
    public ResponseEntity<ApiResponse<Page<BaibaoResponseDTO>>> getBaibaoByTheLoaiID(
            @PathVariable("id") Integer id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Baibao> pageResult = bbService.getBaibaoByTheLoaiID(id, pageable);
        Page<BaibaoResponseDTO> responsePage = pageResult.map(this::convertToDTO);
        ApiResponse<Page<BaibaoResponseDTO>> response = new ApiResponse<>(true, "Fetch bai bao successful", responsePage);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/date/{postDate}")
    public ResponseEntity<ApiResponse<Page<BaibaoResponseDTO>>> getBaibaoByNgayDang(
            @PathVariable("postDate") LocalDate postDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Baibao> pageResult = bbService.getBaibaoByNgayDang(postDate, pageable);
        Page<BaibaoResponseDTO> responsePage = pageResult.map(this::convertToDTO);
        ApiResponse<Page<BaibaoResponseDTO>> response = new ApiResponse<>(true, "Fetch bai bao successful", responsePage);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/date/{date1}/{date2}")
    public ResponseEntity<ApiResponse<Page<BaibaoResponseDTO>>> getBaibaiByNgayDangBetween(
            @PathVariable("date1") LocalDate date1,
            @PathVariable("date2") LocalDate date2,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Baibao> pageResult = bbService.getBaibaiByNgayDangBetween(date1, date2, pageable);
        Page<BaibaoResponseDTO> responsePage = pageResult.map(this::convertToDTO);
        ApiResponse<Page<BaibaoResponseDTO>> response = new ApiResponse<>(true, "Fetch bai bao successful", responsePage);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse<Page<BaibaoResponseDTO>>> getBaibaoByTrangThai(
            @PathVariable("status") Integer status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Baibao> pageResult = bbService.getBaibaoByTrangThai(status, pageable);
        Page<BaibaoResponseDTO> responsePage = pageResult.map(this::convertToDTO);
        ApiResponse<Page<BaibaoResponseDTO>> response = new ApiResponse<>(true, "Fetch bai bao successful", responsePage);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteBaibao(@PathVariable("id") Integer id) {
        bbService.deleteBaibao(id);
        ApiResponse<Void> response = new ApiResponse<>(true, "Delete bai bao successful", null);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Baibao>> createBaibao(@RequestBody Baibao baibao) {
        Baibao bb = bbService.saveBaibao(baibao);
        ApiResponse<Baibao> response = new ApiResponse<>(true, "Create bai bao successful", bb);
        return ResponseEntity.ok().body(response);
    }

    private BaibaoResponseDTO convertToDTO(Baibao baibao) {
        String tieuDe = null;
        Integer so = null;
        Integer tuan = null;

        List<Danhmucbaibao> danhmucbaibaos = baibao.getDanhmucbaibaos();
        for (Danhmucbaibao dmbb : danhmucbaibaos) {
            DanhMuc dm = dmbb.getDanhmuc();
            if (dm != null) {
                tieuDe = dm.getTieuDe();
                so = dm.getSo();
                tuan = dm.getTuan();
                break; // Assuming one-to-one relationship for simplicity
            }
        }

        return new BaibaoResponseDTO(baibao, tieuDe, so, tuan);
    }
}