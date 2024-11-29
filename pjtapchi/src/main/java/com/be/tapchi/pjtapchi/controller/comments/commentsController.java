package com.be.tapchi.pjtapchi.controller.comments;

import com.be.tapchi.pjtapchi.controller.apiResponse.ApiResponse;
import com.be.tapchi.pjtapchi.controller.comments.DTO.DTOBinhluan;
import com.be.tapchi.pjtapchi.jwt.JwtUtil;
import com.be.tapchi.pjtapchi.model.Baibao;
import com.be.tapchi.pjtapchi.model.Binhluan;
import com.be.tapchi.pjtapchi.model.Taikhoan;
import com.be.tapchi.pjtapchi.model.Thich;
import com.be.tapchi.pjtapchi.repository.BaiBaoRepository;
import com.be.tapchi.pjtapchi.repository.BinhluanRepository;
import com.be.tapchi.pjtapchi.service.BinhluanService;
import com.be.tapchi.pjtapchi.userRole.ManageRoles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Controller cho các API liên quan đến Binhluan.
 */
@RestController
@RequestMapping("api/binhluan")
public class commentsController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private BinhluanService binhluanService;

    @Autowired
    private BinhluanRepository binhluanRepository;

    @Autowired
    private BaiBaoRepository baiBaoRepository;

    /**
     * Lấy tất cả các Binhluan.
     *
     * @return ResponseEntity chứa danh sách các Binhluan.
     */

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<Binhluan>>> getExample() {
        List<Binhluan> list = binhluanService.getAllBinhluans();
        ApiResponse<List<Binhluan>> response = new ApiResponse<>(true, "Fetch binh luan successful", list);
        if (!list.isEmpty()) {
            return ResponseEntity.ok().body(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "No binh luan found", null));
        }
    }

    @PostMapping("/add/user")
    public ResponseEntity<?> saveThich(@RequestBody DTOBinhluan entity) {
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
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, "Lỗi khi cmt", e.getMessage()));
        }
        try {
            if(entity.getNoidung() == null){
                return ResponseEntity.badRequest().body(new ApiResponse<>(false, "ko duoc de trong noi dung", null));
            }
            if(entity.getNoidung().isEmpty()){
                return ResponseEntity.badRequest().body(new ApiResponse<>(false, "ko duoc de trong noi dung", null));
            }
            Baibao bb = baiBaoRepository.findById(Integer.valueOf(entity.getBaibaoId())).orElse(null);
            if (bb == null) {
                return ResponseEntity.badRequest().body(new ApiResponse<>(false, "Lỗi không tìm thấy bài báo", null));
            }
            Taikhoan tk = jwtUtil.getTaikhoanFromToken(entity.getToken());
            if (tk == null) {
                return ResponseEntity.badRequest().body(new ApiResponse<>(false, "Lỗi không tìm thấy tai khoan", null));
            }

            //cap nhat like
            Binhluan ubBinhluan = binhluanRepository.findByBaibaoidAndTaikhoanid(Long.valueOf(entity.getBaibaoId()), Long.valueOf(tk.getTaikhoan_id())).orElse(null);
            if(ubBinhluan != null){
                
                return ResponseEntity.ok().body(new ApiResponse<>(true, "Bạn đã bình luận về bài báo này", null));
            }
            
            // tao like moi
            Binhluan bl = new Binhluan();
            bl.setBaibao(bb);
            bl.setTaikhoan(tk);
            bl.setThoigianbl(LocalDateTime.now());
            bl.setStatus(1);
            bl.setNoidung(entity.getNoidung());

            binhluanRepository.save(bl);
            return ResponseEntity.ok().body(new ApiResponse<>(true, "Them binh luan thanh cong", null));
        } catch (Exception e) {
            // TODO: handle exception
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, "Loi khi tao cmt", e.getMessage()));
        }
        
    }

    @PostMapping("/remove/user")
    public ResponseEntity<?> deleThich(@RequestBody DTOBinhluan entity) {
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
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, "Lỗi khi xoa cm", e.getMessage()));
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
            Binhluan ubBinhluan = binhluanRepository.findByBaibaoidAndTaikhoanid(Long.valueOf(entity.getBaibaoId()), Long.valueOf(tk.getTaikhoan_id())).orElse(null);
            if(ubBinhluan == null){
                
                return ResponseEntity.badRequest().body(new ApiResponse<>(false, "Lỗi không tìm thấy bình luận", null));
            }

            if(!tk.getTaikhoan_id().equals(ubBinhluan.getTaikhoan().getTaikhoan_id())){
                return ResponseEntity.badRequest().body(new ApiResponse<>(false, "Tài khoản xóa bl ko phù hợp", null));
            }
            
            binhluanRepository.delete(ubBinhluan);
            
            return ResponseEntity.ok().body(new ApiResponse<>(true, "Đã xóa bình luận", null));
        } catch (Exception e) {
            // TODO: handle exception
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, "Loi khi tao binh luan", e.getMessage()));
        }
        
    }

    @PostMapping("/remove/binhluan")
    public ResponseEntity<?> deleThichAdmin(@RequestBody DTOBinhluan entity) {
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
            if (!jwtUtil.checkRolesFromToken(entity.getToken(), ManageRoles.getADMINRole(),ManageRoles.getEDITORRole())) {
                return ResponseEntity.badRequest().body(new ApiResponse<>(false, "Không được phép can Admin, editor", null));
            }

        } catch (Exception e) {
            // TODO: handle exception
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, "Lỗi khi xoa cmt", e.getMessage()));
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
            Binhluan ubBinhluan = binhluanRepository.findByBaibaoidAndTaikhoanid(Long.valueOf(entity.getBaibaoId()), Long.valueOf(tk.getTaikhoan_id())).orElse(null);
            if(ubBinhluan == null){
                
                return ResponseEntity.badRequest().body(new ApiResponse<>(false, "Lỗi không tìm thấy bình luận", null));
            }

            // if(!tk.getTaikhoan_id().equals(ubBinhluan.getTaikhoan().getTaikhoan_id())){
            //     return ResponseEntity.badRequest().body(new ApiResponse<>(false, "Tài khoản xóa bl ko phù hợp", null));
            // }
            
            binhluanRepository.delete(ubBinhluan);
            
            return ResponseEntity.ok().body(new ApiResponse<>(true, "Đã xóa bình luận", null));
        } catch (Exception e) {
            // TODO: handle exception
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, "Loi khi tao binh luan", e.getMessage()));
        }
        
    }

    

    @PutMapping("/update")
    public ResponseEntity<ApiResponse<Binhluan>> updateBinhluan(@RequestBody Binhluan binhluan) {
        Binhluan updatedBinhluan = binhluanService.updateBinhluan(binhluan);
        return ResponseEntity.ok().body(new ApiResponse<>(true, "Update binh luan successful", updatedBinhluan));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteBinhluan(@PathVariable("id") Long id) {
        binhluanService.deleteBinhluan(id);
        return ResponseEntity.ok().body(new ApiResponse<>(true, "Delete binh luan successful", null));
    }
}
