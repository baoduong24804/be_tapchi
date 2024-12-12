package com.be.tapchi.pjtapchi.controller.admin;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.be.tapchi.pjtapchi.controller.admin.DTOUser.DTOAdmin;
import com.be.tapchi.pjtapchi.controller.admin.DTOUser.DTOResponse.DTORoles;
import com.be.tapchi.pjtapchi.controller.admin.DTOUser.DTOResponse.DTOUser;
import com.be.tapchi.pjtapchi.controller.apiResponse.ApiResponse;
import com.be.tapchi.pjtapchi.jwt.JwtUtil;
import com.be.tapchi.pjtapchi.model.DanhMuc;
import com.be.tapchi.pjtapchi.model.Taikhoan;
import com.be.tapchi.pjtapchi.model.Vaitro;
import com.be.tapchi.pjtapchi.repository.BaiBaoRepository;
import com.be.tapchi.pjtapchi.repository.QuangCaoRepository;
import com.be.tapchi.pjtapchi.repository.TaiKhoanRepository;
import com.be.tapchi.pjtapchi.repository.VaiTroRepository;
import com.be.tapchi.pjtapchi.userRole.ManageRoles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("api/admin")
public class AdminController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private TaiKhoanRepository taiKhoanRepository;

    @Autowired
    private BaiBaoRepository baiBaoRepository;

    @Autowired
    private QuangCaoRepository quangCaoRepository;

    @Autowired
    private VaiTroRepository vaiTroRepository;

    @PostMapping("get/user")
    public ResponseEntity<?> postMethodName(@RequestBody(required = false) DTOAdmin entity,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size) {
        // TODO: process POST request
        if (jwtUtil.checkTokenAndTaiKhoan(entity.getToken()) == false) {
            ApiResponse<?> response = new ApiResponse<>(false, "Tài khoản không hợp lệ", null);
            return ResponseEntity.badRequest().body(response);
        }

        if (!jwtUtil.checkRolesFromToken(entity.getToken(), ManageRoles.getADMINRole())) {
            ApiResponse<?> response = new ApiResponse<>(false, "Can admin", null);
            return ResponseEntity.badRequest().body(response);
        }
        Pageable pageable = PageRequest.of(page, size);
        Page<Taikhoan> list = taiKhoanRepository.findAll(pageable);

        Map<Object, Object> data = new HashMap<>();
        if (list.getContent().size() <= 0) {
            data.put("data", null);
            ApiResponse<?> response = new ApiResponse<>(true, "Ko co du lieu", null);
            return ResponseEntity.ok().body(response);
        }

        List<DTOUser> users = new ArrayList<>();
        for (Taikhoan taikhoan : list.getContent()) {
            DTOUser u = new DTOUser();
            u.setTaikhoanId(taikhoan.getTaikhoan_id() + "");
            u.setEmail(taikhoan.getEmail());
            u.setHovaten(taikhoan.getHovaten());
            if (taikhoan.getGoogleId() == null) {
                u.setGoogle(false);
            } else {
                if (taikhoan.getGoogleId().isEmpty()) {
                    u.setGoogle(false);
                }
                u.setGoogle(true);
            }

            u.setSdt(taikhoan.getSdt() + "");
            u.setStatus(taikhoan.getStatus() + "");
            u.setUrl(taikhoan.getUrl());
            u.setUsername(taikhoan.getUsername());
            Set<DTORoles> roles = new HashSet<>();
            for (Vaitro vt : taikhoan.getVaitro()) {
                DTORoles r = new DTORoles();
                r.setVaitroId(vt.getVaitroId() + "");
                r.setRoles(vt.getTenrole());
                roles.add(r);
            }
            u.setRoles(roles);

            users.add(u);
        }

        data.put("data", users);

        Map<String, Object> phantrang = new HashMap<>();
        phantrang.put("totalPage", String.valueOf(list.getTotalPages()));
        phantrang.put("pageNumber", String.valueOf(list.getNumber()));
        phantrang.put("pageSize", String.valueOf(list.getSize()));
        phantrang.put("totalElements", String.valueOf(list.getTotalElements()));
        data.put("phantrang", phantrang);

        ApiResponse<?> response = new ApiResponse<>(true, "Lay data thanh cong", data);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("update/user/status")
    public ResponseEntity<?> postMethodName(@RequestBody(required = false) DTOAdmin entity) {
        // TODO: process POST request
        if (jwtUtil.checkTokenAndTaiKhoan(entity.getToken()) == false) {
            ApiResponse<?> response = new ApiResponse<>(false, "Tài khoản không hợp lệ", null);
            return ResponseEntity.badRequest().body(response);
        }

        if (!jwtUtil.checkRolesFromToken(entity.getToken(), ManageRoles.getADMINRole())) {
            ApiResponse<?> response = new ApiResponse<>(false, "Can admin", null);
            return ResponseEntity.badRequest().body(response);
        }

        if (entity.getStatus() == null) {
            ApiResponse<?> response = new ApiResponse<>(false, "Status trong", null);
            return ResponseEntity.badRequest().body(response);
        }
        if (entity.getStatus().isEmpty()) {
            ApiResponse<?> response = new ApiResponse<>(false, "Status trong", null);
            return ResponseEntity.badRequest().body(response);
        }

        int status = Integer.valueOf((entity.getStatus() + "").trim());
        if (status < -1 || status > 1) {
            ApiResponse<?> response = new ApiResponse<>(false, "Status khong hop le",
                    "-1: khoa tk, 0: chua kich hoat, 1: hoat dong");
            return ResponseEntity.badRequest().body(response);
        }

        Taikhoan tk = null;
        tk = taiKhoanRepository.findById(Long.valueOf((entity.getTaikhoanId() + "").trim())).orElse(null);
        if (tk == null) {
            ApiResponse<?> response = new ApiResponse<>(false, "Khong tim thay tk", "");
            return ResponseEntity.badRequest().body(response);
        }

        Set<Vaitro> svaitro = tk.getVaitro();
        if (svaitro != null) {
            for (Vaitro vaitro : svaitro) {
                if (vaitro.getTenrole().equalsIgnoreCase(ManageRoles.getADMINRole()) || vaitro.getVaitroId() == 6) {
                    ApiResponse<?> response = new ApiResponse<>(false, "Ko duoc chinh admin", "");
                    return ResponseEntity.badRequest().body(response);
                }
            }
        }

        if (tk.getStatus() == status) {
            ApiResponse<?> response = new ApiResponse<>(true, "Status ko doi :>>", status);
            return ResponseEntity.ok().body(response);
        } else {
            tk.setStatus(status);
            taiKhoanRepository.save(tk);
            ApiResponse<?> response = new ApiResponse<>(true, "Cap nhat thanh cong", status);
            return ResponseEntity.ok().body(response);
        }

    }

    @PostMapping("add/user/role")
    public ResponseEntity<?> updateRole(@RequestBody(required = false) DTOAdmin entity) {
        // TODO: process POST request
        if (jwtUtil.checkTokenAndTaiKhoan(entity.getToken()) == false) {
            ApiResponse<?> response = new ApiResponse<>(false, "Tài khoản không hợp lệ", null);
            return ResponseEntity.badRequest().body(response);
        }

        if (!jwtUtil.checkRolesFromToken(entity.getToken(), ManageRoles.getADMINRole())) {
            ApiResponse<?> response = new ApiResponse<>(false, "Can admin", null);
            return ResponseEntity.badRequest().body(response);
        }

        if (entity.getRole() == null) {
            ApiResponse<?> response = new ApiResponse<>(false, "Role trong", null);
            return ResponseEntity.badRequest().body(response);
        }
        if (entity.getRole().isEmpty()) {
            ApiResponse<?> response = new ApiResponse<>(false, "Role trong", null);
            return ResponseEntity.badRequest().body(response);
        }

        int role = Integer.valueOf((entity.getRole() + "").trim());
        if (role < 1 || role > 6) {
            ApiResponse<?> response = new ApiResponse<>(false, "Role khong hop le",
                    "1: customer, 2:author, 3:censor,4:partner,5:editor,6:admin");
            return ResponseEntity.badRequest().body(response);
        }

        Taikhoan tk = null;
        tk = taiKhoanRepository.findById(Long.valueOf((entity.getTaikhoanId() + "").trim())).orElse(null);
        if (tk == null) {
            ApiResponse<?> response = new ApiResponse<>(false, "Khong tim thay tk", "");
            return ResponseEntity.badRequest().body(response);
        }

        for (Vaitro vt : tk.getVaitro()) {
            if (vt.getVaitroId() == Integer.valueOf(entity.getRole())) {
                ApiResponse<?> response = new ApiResponse<>(true, "Role ko doi vi da co role nay :>>", null);
                return ResponseEntity.ok().body(response);
            }
        }

        Set<Vaitro> svaitro = tk.getVaitro();
        Vaitro addvt = vaiTroRepository.findById(Integer.valueOf(entity.getRole())).orElse(null);
        if (addvt == null) {
            ApiResponse<?> response = new ApiResponse<>(true, "Co loi khi them vai tro", null);
            return ResponseEntity.badRequest().body(response);
        }
        svaitro.add(addvt);
        tk.setVaitro(svaitro);
        taiKhoanRepository.save(tk);
        ApiResponse<?> response = new ApiResponse<>(true, "Them vai tro thanh cong", null);
        return ResponseEntity.ok().body(response);

    }

    @PostMapping("remove/user/role")
    public ResponseEntity<?> remove(@RequestBody(required = false) DTOAdmin entity) {
        // TODO: process POST request
        if (jwtUtil.checkTokenAndTaiKhoan(entity.getToken()) == false) {
            ApiResponse<?> response = new ApiResponse<>(false, "Tài khoản không hợp lệ", null);
            return ResponseEntity.badRequest().body(response);
        }

        if (!jwtUtil.checkRolesFromToken(entity.getToken(), ManageRoles.getADMINRole())) {
            ApiResponse<?> response = new ApiResponse<>(false, "Can admin", null);
            return ResponseEntity.badRequest().body(response);
        }

        if (entity.getRole() == null) {
            ApiResponse<?> response = new ApiResponse<>(false, "Role trong", null);
            return ResponseEntity.badRequest().body(response);
        }
        if (entity.getRole().isEmpty()) {
            ApiResponse<?> response = new ApiResponse<>(false, "Role trong", null);
            return ResponseEntity.badRequest().body(response);
        }

        int role = Integer.valueOf((entity.getRole() + "").trim());
        if (role < 1 || role > 6) {
            ApiResponse<?> response = new ApiResponse<>(false, "Role khong hop le",
                    "1: customer, 2:author, 3:censor,4:partner,5:editor,6:admin");
            return ResponseEntity.badRequest().body(response);
        }

        Taikhoan tk = null;
        tk = taiKhoanRepository.findById(Long.valueOf((entity.getTaikhoanId() + "").trim())).orElse(null);
        if (tk == null) {
            ApiResponse<?> response = new ApiResponse<>(false, "Khong tim thay tk", "");
            return ResponseEntity.badRequest().body(response);
        }

        Set<Vaitro> svaitro = tk.getVaitro();
        Vaitro addvt = vaiTroRepository.findById(Integer.valueOf(entity.getRole())).orElse(null);

        if (addvt == null) {
            ApiResponse<?> response = new ApiResponse<>(false, "Co loi khi xoa vai tro", null);
            return ResponseEntity.badRequest().body(response);
        }

        if (addvt.getTenrole().equalsIgnoreCase(ManageRoles.getADMINRole()) || addvt.getVaitroId() == 6) {
            ApiResponse<?> response = new ApiResponse<>(false, "Ko duoc xoa quyen admin", null);
            return ResponseEntity.badRequest().body(response);
        }

        Set<Vaitro> newvaitro = new HashSet<>();

        for (Vaitro vaitro : svaitro) {
            if(vaitro.getTenrole().equalsIgnoreCase(addvt.getTenrole()) || vaitro.getVaitroId() == addvt.getVaitroId()){
                continue;
            }
            newvaitro.add(vaitro);
        }

     
        tk.setVaitro(newvaitro);
        taiKhoanRepository.save(tk);
        ApiResponse<?> response = new ApiResponse<>(true, "Xoa vai tro thanh cong", null);
        return ResponseEntity.ok().body(response);

    }

    @PostMapping("get/thongke")
    public ResponseEntity<?> getThongKe(@RequestBody(required = false) DTOAdmin entity) {
        // TODO: process POST request
        if (jwtUtil.checkTokenAndTaiKhoan(entity.getToken()) == false) {
            ApiResponse<?> response = new ApiResponse<>(false, "Tài khoản không hợp lệ", null);
            return ResponseEntity.badRequest().body(response);
        }

        if (!jwtUtil.checkRolesFromToken(entity.getToken(), ManageRoles.getADMINRole())) {
            ApiResponse<?> response = new ApiResponse<>(false, "Can admin", null);
            return ResponseEntity.badRequest().body(response);
        }

        long slbaibao = baiBaoRepository.count();
        long sltaikhoan = taiKhoanRepository.count();
        long slquangcao = quangCaoRepository.count();
        double doanhthu = 0.0;

        Map<Object, Object> data = new HashMap<>();

        data.put("slbaibao", slbaibao);
        data.put("sltaikhoan", sltaikhoan);
        data.put("slquangcao", slquangcao);
        data.put("doanhthu", doanhthu);

        Map<Object, Object> result = new HashMap<>();

        result.put("data", data);
        ApiResponse<?> response = new ApiResponse<>(true, "Lay data thanh cong", result);
        return ResponseEntity.ok().body(response);
    }

}