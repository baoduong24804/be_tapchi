package com.be.tapchi.pjtapchi.controller.theloai;

import com.be.tapchi.pjtapchi.controller.apiResponse.ApiResponse;
import com.be.tapchi.pjtapchi.jwt.JwtUtil;
import com.be.tapchi.pjtapchi.model.Taikhoan;
import com.be.tapchi.pjtapchi.model.Theloai;
import com.be.tapchi.pjtapchi.repository.TheloaiRepository;
import com.be.tapchi.pjtapchi.service.TheloaiService;
import com.be.tapchi.pjtapchi.userRole.ManageRoles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/theloai")
public class theloaiController {

    @Autowired
    TheloaiService TheloaiService;

    @Autowired
    private TheloaiRepository theloaiRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/create/theloai")
    public ResponseEntity<ApiResponse<Theloai>> createTheloai(@RequestBody(required = false) DTOTheloai theloai) {
        if (theloai.getTentheloai() == null) {
            ApiResponse<Theloai> response = new ApiResponse<>(false, "Ten the loai ko dc de trong", null);
            return ResponseEntity.ok().body(response);
        }
        if (theloai.getTentheloai().isEmpty()) {
            ApiResponse<Theloai> response = new ApiResponse<>(false, "Ten the loai ko dc de trong", null);
            return ResponseEntity.ok().body(response);
        }
        if (theloai.getToken() == null) {
            ApiResponse<Theloai> response = new ApiResponse<>(false, "Token de trong", null);
            return ResponseEntity.ok().body(response);
        }
        if (theloai.getToken().isEmpty()) {
            ApiResponse<Theloai> response = new ApiResponse<>(false, "Token de trong", null);
            return ResponseEntity.ok().body(response);
        }
        Taikhoan tk = null;
        tk = jwtUtil.getTaikhoanFromToken(theloai.getToken());
        if(tk == null){
            ApiResponse<Theloai> response = new ApiResponse<>(false, "Khong duoc phep", null);
            return ResponseEntity.ok().body(response);
        }
        if(!jwtUtil.checkRolesFromToken(theloai.getToken(), ManageRoles.getADMINRole(),ManageRoles.getEDITORRole())){
            ApiResponse<Theloai> response = new ApiResponse<>(false, "Can quyen admin va editor", null);
            return ResponseEntity.ok().body(response);
        }
        Theloai tl = new Theloai();
        tl.setTenloai(theloai.getTentheloai()+"".trim());
        theloaiRepository.save(tl);
        ApiResponse<Theloai> response = new ApiResponse<>(true, "Tao the loai thanh cong", null);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/update/theloai")
    public ResponseEntity<ApiResponse<Theloai>> updateTheloai(@RequestBody(required = false) DTOTheloai theloai) {
        if (theloai.getTentheloai() == null) {
            ApiResponse<Theloai> response = new ApiResponse<>(false, "Ten the loai ko dc de trong", null);
            return ResponseEntity.ok().body(response);
        }
        if (theloai.getTentheloai().isEmpty()) {
            ApiResponse<Theloai> response = new ApiResponse<>(false, "Ten the loai ko dc de trong", null);
            return ResponseEntity.ok().body(response);
        }
        if (theloai.getTheloaiId() == null) {
            ApiResponse<Theloai> response = new ApiResponse<>(false, "The loai ID ko dc de trong", null);
            return ResponseEntity.ok().body(response);
        }
        if (theloai.getTheloaiId().isEmpty()) {
            ApiResponse<Theloai> response = new ApiResponse<>(false, "The loai ID ko dc de trong", null);
            return ResponseEntity.ok().body(response);
        }
        if (theloai.getToken() == null) {
            ApiResponse<Theloai> response = new ApiResponse<>(false, "Token de trong", null);
            return ResponseEntity.ok().body(response);
        }
        if (theloai.getToken().isEmpty()) {
            ApiResponse<Theloai> response = new ApiResponse<>(false, "Token de trong", null);
            return ResponseEntity.ok().body(response);
        }
        Taikhoan tk = null;
        tk = jwtUtil.getTaikhoanFromToken(theloai.getToken());
        if(tk == null){
            ApiResponse<Theloai> response = new ApiResponse<>(false, "Khong duoc phep", null);
            return ResponseEntity.ok().body(response);
        }
        if(!jwtUtil.checkRolesFromToken(theloai.getToken(), ManageRoles.getADMINRole(),ManageRoles.getEDITORRole())){
            ApiResponse<Theloai> response = new ApiResponse<>(false, "Can quyen admin va editor", null);
            return ResponseEntity.ok().body(response);
        }
        Theloai tl = null;
        tl = theloaiRepository.findById(Integer.valueOf(theloai.getTheloaiId())).orElse(null);
        if(tl == null){
            ApiResponse<Theloai> response = new ApiResponse<>(false, "Loi tim the loai", null);
            return ResponseEntity.ok().body(response);
        }
        tl.setTenloai(theloai.getTentheloai()+"".trim());
        theloaiRepository.save(tl);
        ApiResponse<Theloai> response = new ApiResponse<>(true, "Cap nhat the loai thanh cong", null);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteTheloai(@PathVariable("id") Integer id) {
        TheloaiService.deleteTheloai(id);
        return ResponseEntity.ok().body(new ApiResponse<>(true, "Delete the loai successful", null));
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<Iterable<Theloai>>> getAllTheloais() {
        Iterable<Theloai> list = TheloaiService.getAllTheloais();
        ApiResponse<Iterable<Theloai>> response = new ApiResponse<>(true, "Fetch the loai successful", list);
        return ResponseEntity.ok().body(response);
    }
}
