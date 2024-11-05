package com.be.tapchi.pjtapchi.controller.user;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.be.tapchi.pjtapchi.controller.apiResponse.ApiResponse;
import com.be.tapchi.pjtapchi.controller.user.model.ChangePassword;
import com.be.tapchi.pjtapchi.controller.user.model.LoginRequest;
import com.be.tapchi.pjtapchi.controller.user.model.UserRegister;
import com.be.tapchi.pjtapchi.jwt.JwtUtil;
import com.be.tapchi.pjtapchi.model.Taikhoan;
import com.be.tapchi.pjtapchi.model.Taikhoanchitiet;
import com.be.tapchi.pjtapchi.model.Vaitro;

import com.be.tapchi.pjtapchi.repository.TaiKhoanRepository;
import com.be.tapchi.pjtapchi.repository.VaiTroRepository;
import com.be.tapchi.pjtapchi.service.TaiKhoanService;
import com.be.tapchi.pjtapchi.service.TaikhoanTokenService;
import com.be.tapchi.pjtapchi.userRole.ManageRoles;
import com.be.tapchi.pjtapchi.userRole.RoleName;

import io.jsonwebtoken.Claims;
import jakarta.validation.Valid;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/user")
public class UserController {
    @Autowired
    TaiKhoanRepository taiKhoanRepository;

    @Autowired
    private TaiKhoanService taiKhoanService;

    @Autowired
    VaiTroRepository vaiTroRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private TaikhoanTokenService taikhoanTokenService;

    // public int getUserRoleByName(String nameRole){
    // Vaitro vt = vaiTroRepository.findBytenrole(nameRole);
    // if(vt == null){
    // return 1;
    // }
    // return vt.getVaitroId();
    // }

    @PostMapping("checkRoles")
    public ResponseEntity<?> postMethodName(@RequestParam String token) {
        // TODO: process POST request

        return ResponseEntity.ok()
                .body(jwtUtil.checkRolesFromToken(token, ManageRoles.getAUTHORRole(), ManageRoles.getADMINRole()));
    }

    @PostMapping("get/userDetail")
    public ResponseEntity<ApiResponse<?>> userDetail(@RequestParam String token) {
        try {
            ApiResponse<?> api = new ApiResponse<>();

            Claims claims = jwtUtil.extractClaims(token);
            // neu token ko dung hoac bi loi
            if (claims == null) {
                api.setSuccess(false);
                api.setMessage(HttpStatus.NON_AUTHORITATIVE_INFORMATION.toString());

                return ResponseEntity.badRequest().body(api);
            }
            String username = claims.getSubject();
            // kiem tra token hop le va con hsd
            if (!jwtUtil.validateToken(token, username)) {
                api.setSuccess(false);
                api.setMessage(HttpStatus.UNAUTHORIZED.toString());

                return ResponseEntity.badRequest().body(api);
            }
            // neu token sap het han -> refesh token
            String newToken = jwtUtil.refeshToken(claims);

            Taikhoan tk = taiKhoanService.findByUsername(username);
            if (tk == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            Taikhoanchitiet ct = tk.getTaikhoanchitiet();
            Map<String, String> data = new HashMap<>();
            Map<String, String> userData = new HashMap<>();
            SimpleDateFormat fmd = new SimpleDateFormat("dd-MM-yyyy");

            userData.put("date", fmd.format(ct.getNgaytao()));

            Set<String> roles = new HashSet<>();
            for (Vaitro vt : tk.getVaitro()) {
                roles.add(vt.getVaitroId() + ":" + vt.getTenrole());
            }
            userData.put("roles", roles.toString());
            userData.put("url", ct.getUrl());
            userData.put("phone", ct.getSdt());
            userData.put("email", ct.getEmail());
            userData.put("fullname", ct.getHovaten());
            userData.put("username", ct.getTaikhoan().getUsername());
            data.put("user", userData.toString());
            if (newToken != null) {
                data.put("newToken", newToken);
            }

            ApiResponse<?> response = new ApiResponse<>(true, "Fetch successful", data);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            // TODO: handle exception
            throw e;
        }

    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<?>> createAuthenticationToken(@RequestBody LoginRequest loginRequest)
            throws Exception {
        ApiResponse<?> response = new ApiResponse<>();
        try {

            // kiem tra tai khoan ton tai
            if (!taiKhoanService.checkTaikhoan(loginRequest)) {
                response.setSuccess(false);
                response.setMessage("Sai tk hoac mk");
                response.setData(null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            // login that bai, co tk nhung sai mk
            if (!taiKhoanService.loginTaikhoan(loginRequest)) {
                response.setSuccess(false);
                response.setMessage("Sai tk hoac mk");
                response.setData(null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

        } catch (AuthenticationException e) {
            throw new Exception("Loi khong mong muon khi login", e);
        }

        // Nếu xác thực thành công, tạo JWT

        Taikhoan tk = taiKhoanService.findByUsername(loginRequest.getUsername());
        if (tk == null) {
            tk = taiKhoanService.findByEmail(loginRequest.getUsername());
        }
        response.setMessage("Dang nhap thanh cong");
        response.setSuccess(true);
        Map<String, String> map = new HashMap<>();
        map.put("token", jwtUtil.generateToken(tk.getUsername()));
        // co the luu token vao dtb
        Set<String> roles = new HashSet<>();
        for (Vaitro vt : tk.getVaitro()) {
            roles.add(vt.getVaitroId() + ":" + vt.getTenrole());
        }
        map.put("roles", roles.toString());
        response.setData(map.toString());
        return ResponseEntity.ok().body(response);

    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<?>> userRegister(@Valid @RequestBody(required = true) UserRegister userRegister,
            BindingResult bindingResult) {
        // TODO: process POST request
        ApiResponse<?> api = new ApiResponse<>();
        // kiem tra du lieu bi trong
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getFieldErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.joining(", "));
            api.setSuccess(false);
            api.setMessage("Loi de trong du lieu");
            api.setData(errorMessage);
            return ResponseEntity.badRequest().body(api);
        }
        try {

            if (taiKhoanRepository.existsByUsername(userRegister.getUsername())) {
                api.setSuccess(false);
                api.setData(null);
                api.setMessage("Username ton tai");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(api);
            }
            // kiem tra email
            if (taiKhoanService.existsByEmail(userRegister.getEmail().trim())) {
                api.setSuccess(false);
                api.setMessage("Email da ton tai");
                api.setData(null);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(api);
            }

            // kiem tra sdt
            if (taiKhoanService.existsBySdt(userRegister.getSdt().trim())) {
                api.setSuccess(false);
                api.setMessage("Sdt da ton tai");
                api.setData(null);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(api);
            }

            Taikhoan tk = new Taikhoan();
            tk.setUsername(userRegister.getUsername());
            tk.setPassword(passwordEncoder.encode(userRegister.getPassword()));
            Vaitro vt = vaiTroRepository.findBytenrole(RoleName.USER.toString());
            // Vaitro vt2 = vaiTroRepository.findBytenrole(RoleName.AUTHOR.toString());
            // tra ve loi neu ko tim thay vai tro
            List<Vaitro> vaitros = new ArrayList<>();
            vaitros.add(vt);
            // vaitros.add(vt2);
            if (vt == null) {
                api.setSuccess(false);
                api.setMessage("Loi khi tao vai tro");
                api.setData(null);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(api);
            }
            Set<Vaitro> setVaitros = new HashSet<>();
            for (Vaitro vaitro : vaitros) {
                setVaitros.add(vaitro);
            }
            tk.setVaitro(setVaitros);

            // tai khoan chi tiet
            Taikhoanchitiet tkct = new Taikhoanchitiet();
            tkct.setHovaten(userRegister.getHovaten());
            tkct.setEmail(userRegister.getEmail());
            tkct.setNgaytao(new Date());
            tkct.setSdt(userRegister.getSdt());
            tkct.setUrl(userRegister.getUrl());
            tkct.setStatus(1);
            try {
                taiKhoanService.saveTaiKhoanAndChiTiet(tk, tkct);
                api.setSuccess(true);
                api.setMessage("Dang ky thanh cong");
                api.setData(null);
                return ResponseEntity.ok().body(api);
            } catch (Exception e) {
                // TODO: handle exception
                return null;
            }

        } catch (Exception e) {
            // TODO: handle exception
            api.setSuccess(false);
            api.setMessage(e.getMessage());
            api.setData(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(api);
        }

    }

    @PostMapping("/changepassword")
    public ResponseEntity<?> postchangepassword(@Valid @RequestBody(required = true) ChangePassword entity,
            BindingResult bindingResult) {
        // TODO: process POST request
        ApiResponse<?> api = new ApiResponse<>();
        // kiem tra du lieu bi trong
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getFieldErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.joining(", "));
            api.setSuccess(false);
            api.setMessage("Loi de trong du lieu");
            api.setData(errorMessage);
            return ResponseEntity.badRequest().body(api);
        }
        // neu tk va mk sai
        if (!taiKhoanService.loginTaikhoan(entity)) {
            api.setSuccess(false);
            api.setMessage("Sai tk or mk");
            api.setData(null);
            return ResponseEntity.badRequest().body(api);
        }

        Taikhoan tk = taiKhoanService.findByUsername(entity.getUsername());
        if (tk == null) {
            tk = taiKhoanService.findByEmail(entity.getUsername());
        }

        tk.setPassword(passwordEncoder.encode(entity.getNewpassword().trim()));

        taiKhoanService.saveTaiKhoan(tk);
        

        api.setSuccess(true);
        api.setMessage("Doi mk thanh cong");
        api.setData(null);
        return ResponseEntity.badRequest().body(api);

    }

    // quen mk
    @PostMapping("/forgot")
    public ResponseEntity<ApiResponse<?>> forgotPassword(@RequestParam("email") String email) {
        try {
            taikhoanTokenService.createPasswordResetTokenForUser(email);
            ApiResponse<?> api = new ApiResponse<>();
            api.setSuccess(true);
            api.setMessage("Kiem tra email de doi mk");
            api.setData(null);
            return ResponseEntity.ok().body(api);
        } catch (Exception e) {
            // TODO: handle exception
            return ResponseEntity.badRequest().body(null);
        }

    }

    // doi mk
    @PostMapping("/reset")
    public ResponseEntity<ApiResponse<?>> resetPassword(@RequestParam("token") String token,
            @RequestParam("password") String newPassword) {
        try {
            taikhoanTokenService.changePassword(token, newPassword);
            ApiResponse<?> api = new ApiResponse<>();
            api.setSuccess(true);
            api.setMessage("Doi mk thanh cong");
            api.setData(null);
            return ResponseEntity.ok().body(api);
        } catch (Exception e) {
            // TODO: handle exception
            return ResponseEntity.badRequest().body(null);
        }

    }

}
