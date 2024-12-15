package com.be.tapchi.pjtapchi.controller.like;

import com.be.tapchi.pjtapchi.controller.apiResponse.ApiResponse;
import com.be.tapchi.pjtapchi.controller.baibao.baibaoController;
import com.be.tapchi.pjtapchi.controller.like.DTO.DTODaxem;
import com.be.tapchi.pjtapchi.controller.like.DTO.DTOLike;
import com.be.tapchi.pjtapchi.controller.like.DTO.DTOLikeRep;
import com.be.tapchi.pjtapchi.jwt.JwtUtil;
import com.be.tapchi.pjtapchi.model.Baibao;
import com.be.tapchi.pjtapchi.model.Danhmucbaibao;
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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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

    public String formatDate(String originalTimeStr) {

        // Định dạng để phân tích chuỗi thời gian ban đầu
        DateTimeFormatter originalFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS");

        // Chuyển chuỗi thành đối tượng LocalDateTime
        LocalDateTime dateTime = LocalDateTime.parse(originalTimeStr, originalFormatter);

        // Định dạng đầu ra mới
        DateTimeFormatter newFormatter = DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy");

        // Chuyển đổi sang định dạng mới
        String formattedTime = dateTime.format(newFormatter);

        return formattedTime;

    }

    @PostMapping("get/user/daxem")
    public ResponseEntity<?> daxem(@RequestBody DTOLike entity) {
        if (jwtUtil.checkTokenAndTaiKhoan(entity.getToken()) == false) {
            ApiResponse<?> response = new ApiResponse<>(false, "Tài khoản không hợp lệ", null);
            return ResponseEntity.badRequest().body(response);
        }

        if (!jwtUtil.checkRolesFromToken(entity.getToken(), ManageRoles.getCUSTOMERRole())) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, "Không được phép can CUSTOMER", null));
        }

        Taikhoan tk = null;
        tk = jwtUtil.getTaikhoanFromToken(entity.getToken());
        if (tk == null) {
            ApiResponse<?> response = new ApiResponse<>(false, "Tai khoan trong", null);
            return ResponseEntity.badRequest().body(response);
        }
        List<Thich> list = thichRepository.findByTaikhoanid(tk.getTaikhoan_id());
        List<DTODaxem> data = new ArrayList<>();
        for (Thich thich : list) {

            DTODaxem item = new DTODaxem();
            item.setTenbaibao(thich.getBaibao().getTieude());
            //item.setThoigian(formatDate(thich.getThoigianthich() + ""));
            item.setBaibaoId(thich.getBaibao().getId()+"");
            item.setTenbaibao(thich.getBaibao().getTieude());
            if(thich.getBaibao().getDanhmucbaibaos() != null){
                for (Danhmucbaibao  dmbb : thich.getBaibao().getDanhmucbaibaos()) {
                    item.setTendanhmuc(dmbb.getDanhmuc().getTieude());
                    break;
                }
            }

            
            data.add(item);

        }
        ApiResponse<?> response = new ApiResponse<>(true, "Fetch thich successful", data);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("get/user/like")
    public ResponseEntity<?> getlike(@RequestBody DTOLike entity) {
        if (jwtUtil.checkTokenAndTaiKhoan(entity.getToken()) == false) {
            ApiResponse<?> response = new ApiResponse<>(false, "Tài khoản không hợp lệ", null);
            return ResponseEntity.badRequest().body(response);
        }

        if (!jwtUtil.checkRolesFromToken(entity.getToken(), ManageRoles.getCUSTOMERRole())) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, "Không được phép can CUSTOMER", null));
        }

        Taikhoan tk = null;
        tk = jwtUtil.getTaikhoanFromToken(entity.getToken());
        if (tk == null) {
            ApiResponse<?> response = new ApiResponse<>(false, "Tai khoan trong", null);
            return ResponseEntity.badRequest().body(response);
        }
        List<Thich> list = thichRepository.findByTaikhoanid(tk.getTaikhoan_id());
        List<DTOLikeRep> data = new ArrayList<>();
        for (Thich thich : list) {
            if (thich.getStatus() == 1) {
                DTOLikeRep item = new DTOLikeRep();
                item.setTenbaibao(thich.getBaibao().getTieude());
                item.setThoigian(formatDate(thich.getThoigianthich() + ""));
                item.setBaibaoId(thich.getBaibao().getId()+"");
                if(thich.getBaibao().getDanhmucbaibaos() != null){
                    for (Danhmucbaibao  dmbb : thich.getBaibao().getDanhmucbaibaos()) {
                        item.setTendanhmuc(dmbb.getDanhmuc().getTieude());
                        break;
                    }
                }
                data.add(item);
            }

        }
        ApiResponse<?> response = new ApiResponse<>(true, "Fetch thich successful", data);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/add/user")
    public ResponseEntity<?> saveThich(@RequestBody DTOLike entity) {
        try {
            if (jwtUtil.checkTokenAndTaiKhoan(entity.getToken()) == false) {
                ApiResponse<?> response = new ApiResponse<>(false, "Tài khoản không hợp lệ", null);
                return ResponseEntity.badRequest().body(response);
            }
            if (entity.getBaibaoId() == null) {
                return ResponseEntity.badRequest().body(new ApiResponse<>(false, "Lỗi baibaoId", null));
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

            // cap nhat like
            Thich ulike = thichRepository
                    .findByBaibaoidAndTaikhoanid(Long.valueOf(entity.getBaibaoId()), Long.valueOf(tk.getTaikhoan_id()))
                    .orElse(null);
            if (ulike != null) {
                ulike.setStatus(ulike.getStatus() == 1 ? 0 : 1);
                thichRepository.save(ulike);
                return ResponseEntity.ok().body(new ApiResponse<>(true, "Cap nhat like thanh cong", ulike.getStatus()));
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
                    // 0 chua thich
                    th.setStatus(0);
                } else {
                    // 1 : thich
                    th.setStatus(1);
                }
            }

            thichService.save(th);
            return ResponseEntity.ok().body(new ApiResponse<>(true, "Them like thanh cong", th.getStatus()));
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
