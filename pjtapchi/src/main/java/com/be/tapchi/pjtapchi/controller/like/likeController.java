package com.be.tapchi.pjtapchi.controller.like;

import com.be.tapchi.pjtapchi.controller.apiResponse.ApiResponse;
import com.be.tapchi.pjtapchi.controller.baibao.baibaoController;
import com.be.tapchi.pjtapchi.controller.like.DTO.DTOLike;
import com.be.tapchi.pjtapchi.jwt.JwtUtil;
import com.be.tapchi.pjtapchi.model.Baibao;
import com.be.tapchi.pjtapchi.model.Taikhoan;
import com.be.tapchi.pjtapchi.model.Thich;
import com.be.tapchi.pjtapchi.repository.BaiBaoRepository;
import com.be.tapchi.pjtapchi.repository.ThichRepository;
import com.be.tapchi.pjtapchi.service.ThichService;
import com.be.tapchi.pjtapchi.userRole.ManageRoles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/like")
public class likeController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private ThichRepository thichRepository;

    @Autowired
    private ThichService thichService;

    @Autowired
    private BaiBaoRepository baiBaoRepository;

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

    @PostMapping("/add/user")
    public ResponseEntity<?> saveThich(@RequestBody DTOLike entity) {
        try {
            if (entity.getToken() == null) {
                return ResponseEntity.badRequest().body(new ApiResponse<>(false, "Lỗi token", null));
            }
            if (entity.getBaibaoId() == null) {
                return ResponseEntity.badRequest().body(new ApiResponse<>(false, "Lỗi baibaoId", null));
            }
            if (jwtUtil.getTaikhoanFromToken(entity.getToken()) == null) {
                return ResponseEntity.badRequest().body(new ApiResponse<>(false, "Không được phép", null));
            }
            if (!jwtUtil.checkRolesFromToken(entity.getToken(), ManageRoles.getCUSTOMERRole())) {
                return ResponseEntity.badRequest().body(new ApiResponse<>(false, "Không được phép can CUSTOMER", null));
            }

        } catch (Exception e) {
            // TODO: handle exception
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, "Lỗi khi like", null));
        }
        try {
            Baibao bb = baiBaoRepository.findById(Integer.valueOf(entity.getBaibaoId())).orElse(null);
            if (bb == null) {
                return ResponseEntity.badRequest().body(new ApiResponse<>(false, "Lỗi không tìm thấy bài báo", null));
            }
            Taikhoan tk = jwtUtil.getTaikhoanFromToken(entity.getToken());
            if (tk == null) {
                return ResponseEntity.badRequest().body(new ApiResponse<>(false, "Lỗi không tìm thấy tai khoan", null));
            }

            //cap nhat like
            Thich ulike = thichRepository.findByBaibaoidAndTaikhoanid(Long.valueOf(entity.getBaibaoId()), Long.valueOf(tk.getTaikhoan_id())).orElse(null);
            if(ulike != null){
                ulike.setStatus(ulike.getStatus() == 1 ? 2 : 1);
                thichRepository.save(ulike);
                return ResponseEntity.ok().body(new ApiResponse<>(true, "Cap nhat like thanh cong", null));
            }
            
            // tao like moi
            Thich th = new Thich();
            th.setBaibao(bb);
            th.setTaikhoan(tk);
            th.setThoigianthich(LocalDateTime.now());
            if (entity.getStatus() == null) {
                th.setStatus(0);
            } else {
                if (entity.getStatus().isEmpty()) {
                    th.setStatus(0);
                } else {
                    th.setStatus(1);
                }
            }

            thichService.save(th);
            return ResponseEntity.ok().body(new ApiResponse<>(true, "Them like thanh cong", null));
        } catch (Exception e) {
            // TODO: handle exception
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, "Loi khi tao like", e.getMessage()));
        }
        
    }

    @PostMapping("/unlike")
    public ResponseEntity<ApiResponse<Void>> deleteThich(Long id) {
        thichService.deleteById(id);
        return ResponseEntity.ok().body(new ApiResponse<>(true, "Delete thich successful", null));
    }
}
