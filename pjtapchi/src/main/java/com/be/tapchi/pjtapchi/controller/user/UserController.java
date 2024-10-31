package com.be.tapchi.pjtapchi.controller.user;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.be.tapchi.pjtapchi.controller.apiResponse.ApiResponse;
import com.be.tapchi.pjtapchi.controller.user.model.LoginRequest;
import com.be.tapchi.pjtapchi.controller.user.model.UserRegister;
import com.be.tapchi.pjtapchi.jwt.JwtUtil;
import com.be.tapchi.pjtapchi.model.Kiemduyet;
import com.be.tapchi.pjtapchi.model.Taikhoan;
import com.be.tapchi.pjtapchi.model.Taikhoanchitiet;
import com.be.tapchi.pjtapchi.model.Vaitro;

import com.be.tapchi.pjtapchi.repository.TaiKhoanRepository;
import com.be.tapchi.pjtapchi.repository.VaiTroRepository;
import com.be.tapchi.pjtapchi.service.TaiKhoanService;
import com.be.tapchi.pjtapchi.userRole.RoleName;

import jakarta.validation.Valid;

import java.util.List;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("api")
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

    // public int getUserRoleByName(String nameRole){
    // Vaitro vt = vaiTroRepository.findBytenrole(nameRole);
    // if(vt == null){
    // return 1;
    // }
    // return vt.getVaitroId();
    // }

    @GetMapping("get/userDetail")
    public ResponseEntity<ApiResponse<?>> userDetail(@RequestParam String username) {
        Taikhoan tk = taiKhoanRepository.findByUsername(username);

        if (tk == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        Taikhoanchitiet ct = tk.getTaikhoanchitiet();
        ApiResponse<?> response = new ApiResponse<>(true, "Fetch successful", ct);
        return ResponseEntity.ok().body(response);

    }

    @PostMapping("/user/login")
    public ResponseEntity<ApiResponse<?>> createAuthenticationToken(@RequestBody LoginRequest loginRequest)
            throws Exception {
        ApiResponse<?> response = new ApiResponse<>();
        try {

            // neu login ko thang cong
            if (!taiKhoanService.loginTaikhoan(loginRequest)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

        } catch (AuthenticationException e) {
            throw new Exception("Loi khong mong muon khi login", e);
        }

        // Nếu xác thực thành công, tạo JWT

        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());
        response.setMessage("Dang nhap thanh cong");
        response.setSuccess(true);
        response.setData(jwtUtil.generateToken(userDetails.getUsername()));
        return ResponseEntity.ok().body(response);

    }

    @PostMapping("user/register")
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

            Taikhoan tk = new Taikhoan();
            tk.setUsername(userRegister.getUsername());
            tk.setPassword(passwordEncoder.encode(userRegister.getPassword()));
            Vaitro vt = vaiTroRepository.findBytenrole(RoleName.USER.toString());
            // tra ve loi neu ko tim thay vai tro
            if (vt == null) {
                api.setSuccess(false);
                api.setMessage("Loi khi tao vai tro");
                api.setData(null);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(api);
            }
            tk.setVaitro(Set.of(vt));
            // kiem tra email
            if (taiKhoanService.existsByEmail(userRegister.getEmail().trim())) {
                api.setSuccess(false);
                api.setMessage("Email da ton tai");
                api.setData(null);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(api);
            }
            // tai khoan chi tiet
            Taikhoanchitiet tkct = new Taikhoanchitiet();
            tkct.setHovaten(userRegister.getHovaten());
            tkct.setEmail(userRegister.getEmail());
            tkct.setNgaytao(userRegister.getNgaytao());
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

}
