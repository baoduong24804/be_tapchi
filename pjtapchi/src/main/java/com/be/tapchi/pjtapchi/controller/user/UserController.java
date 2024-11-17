package com.be.tapchi.pjtapchi.controller.user;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.be.tapchi.pjtapchi.controller.apiResponse.ApiResponse;
import com.be.tapchi.pjtapchi.controller.user.model.ChangePassword;
import com.be.tapchi.pjtapchi.controller.user.model.LoginRequest;
import com.be.tapchi.pjtapchi.controller.user.model.ResetPassword;
import com.be.tapchi.pjtapchi.controller.user.model.UserRegister;
import com.be.tapchi.pjtapchi.controller.user.model.UserRegisterByGG;
import com.be.tapchi.pjtapchi.jwt.JwtUtil;
import com.be.tapchi.pjtapchi.model.Taikhoan;

import com.be.tapchi.pjtapchi.model.Vaitro;

import com.be.tapchi.pjtapchi.repository.TaiKhoanRepository;
import com.be.tapchi.pjtapchi.repository.VaiTroRepository;
import com.be.tapchi.pjtapchi.service.EmailService;
import com.be.tapchi.pjtapchi.service.RateLimitingService;
import com.be.tapchi.pjtapchi.service.TaiKhoanService;
import com.be.tapchi.pjtapchi.service.TaikhoanTokenService;
import com.be.tapchi.pjtapchi.userRole.ManageRoles;
import com.be.tapchi.pjtapchi.userRole.RoleName;

import io.github.bucket4j.Bucket;
import io.jsonwebtoken.Claims;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
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
import org.springframework.web.bind.annotation.PathVariable;
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

    // @Autowired
    // private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private EmailService emailService;

    @Autowired
    private RateLimitingService rateLimitingService;

    // @Autowired
    // private UserDetailsService userDetailsService;

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
    public ResponseEntity<ApiResponse<?>> userDetail(@RequestBody(required = false) LoginRequest loginRequest) {
        try {
            ApiResponse<?> api = new ApiResponse<>();
            
            Claims claims = jwtUtil.extractClaims(loginRequest.getToken());
            // neu token ko dung hoac bi loi
            if (claims == null) {
                api.setSuccess(false);
                api.setMessage(HttpStatus.NON_AUTHORITATIVE_INFORMATION.toString());

                return ResponseEntity.badRequest().body(api);
            }
            String username = claims.getSubject();
            // kiem tra token hop le va con hsd
            if (!jwtUtil.validateToken(loginRequest.getToken(), username)) {
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

            Map<String, Object> data = new HashMap<>();
            Map<String, Object> userData = new HashMap<>();
            Map<String, Object> roles = new HashMap<>();
            SimpleDateFormat fmd = new SimpleDateFormat("dd-MM-yyyy");

            userData.put("date", fmd.format(tk.getNgaytao()));

            for (Vaitro vt : tk.getVaitro()) {
                roles.put(String.valueOf(vt.getVaitroId()), vt.getTenrole());
            }
            userData.put("roles", roles);
            userData.put("url", tk.getUrl());
            userData.put("phone", tk.getSdt());
            userData.put("email", tk.getEmail());
            userData.put("fullname", tk.getHovaten());
            userData.put("username", tk.getUsername());
            data.put("user", userData);
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
                response.setMessage("Tài khoản hoặc mật khẩu không đúng");
                response.setData(null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            // login that bai, co tk nhung sai mk
            if (!taiKhoanService.loginTaikhoan(loginRequest)) {
                response.setSuccess(false);
                response.setMessage("Tài khoản hoặc mật khẩu không đúng");
                response.setData(null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

        } catch (AuthenticationException e) {
            throw new Exception("Lỗi không mong muốn khi đăng nhập", e);
        }

        // Nếu xác thực thành công, tạo JWT

        Taikhoan tk = taiKhoanService.getTaikhoanLogin(loginRequest);//////
        if (tk == null) {

            response.setSuccess(false);

            response.setMessage("Có lỗi khi đăng nhập vui lòng thử lại sau");

            return ResponseEntity.badRequest().body(response);
        }

        // kiem tra tk bi khoa hoac chua kich hoat
        if (taiKhoanService.checkUserLockedorNotActice(tk)) {
            if (tk.getStatus() == -1) {
                response.setSuccess(false);
                response.setMessage("Tài khoản của bạn đã bị khóa vui lòng liên hệ với admin");
                return ResponseEntity.badRequest().body(response);
            }
            if (tk.getStatus() == 0) {
                response.setSuccess(false);
                response.setMessage("Vui lòng kích hoạt tài khoản");
                return ResponseEntity.badRequest().body(response);
            }
        }

        // dang nhap thanh cong
        response.setMessage("Đăng nhập thành công");
        response.setSuccess(true);
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> roles = new HashMap<>();
        map.put("token", jwtUtil.generateToken(tk.getUsername()));
        // co the luu token vao dtb
        for (Vaitro vt : tk.getVaitro()) {
            roles.put(String.valueOf(vt.getVaitroId()), vt.getTenrole());
        }

        map.put("roles", roles);
        map.put("fullname", tk.getHovaten());
        response.setData(map);
        return ResponseEntity.ok().body(response);

    }

    public String generateUsernameByGG(String input) {
        try {
            String cleanedInput = input.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();

            String randomUUID = UUID.randomUUID().toString().replace("-", "").substring(0, 6);
            String username = cleanedInput + randomUUID;
            for (int i = 0; i < 99; i++) {
                if (taiKhoanService.existsByUsername(username)) {
                    randomUUID = UUID.randomUUID().toString().replace("-", "").substring(0, 6);
                    username = cleanedInput + randomUUID;
                } else {
                    return username.trim();
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
        }

        return null;
    }

    public String generateUsername(String input) {

        String cleanedInput = input.replaceAll("[^a-zA-Z0-9]", "");

        return cleanedInput.toLowerCase();
    }

    @PostMapping("/login/google")
    public ResponseEntity<ApiResponse<?>> loginorregisterByGG(@Valid @RequestBody UserRegisterByGG entity,
            BindingResult bindingResult) {
        // TODO: process POST request
        ApiResponse<?> api = new ApiResponse<>();
        
        // kiem tra sub de trong
        try {
            if (entity.getSub() == null || entity.getSub().isEmpty()) {
                api.setSuccess(false);
                api.setMessage("Có lỗi khi đăng nhập với Google");
                api.setData(null);
                return ResponseEntity.badRequest().body(api);
            }
            if (taiKhoanRepository.existsByGoogleId(entity.getSub())) {
                Taikhoan tk = taiKhoanRepository.findByGoogleId(entity.getSub().trim());
                if (tk == null) {
                    api.setSuccess(false);
                    api.setMessage("Có lỗi không mong muốn xảy ra");
                    return ResponseEntity.badRequest().body(api);
                }
                api.setSuccess(true);
                api.setMessage("Đăng nhập thành công với tài khoản Google");
                Map<String, Object> map = new HashMap<>();
                Map<String, Object> roles = new HashMap<>();
                map.put("token", jwtUtil.generateToken(tk.getUsername()));
                // co the luu token vao dtb
                for (Vaitro vtro : tk.getVaitro()) {
                    roles.put(String.valueOf(vtro.getVaitroId()), vtro.getTenrole());
                }

                map.put("roles", roles);
                map.put("fullname", tk.getHovaten());
                api.setData(map);
                return ResponseEntity.ok().body(api);
            }
        } catch (Exception e) {
            // TODO: handle exception
            return ResponseEntity.badRequest().body(api);
        }

        // kiem tra du lieu bi trong
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getFieldErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.joining(", "));
            api.setSuccess(false);
            api.setMessage("Không được để trống dữ liệu");
            api.setData(errorMessage);
            return ResponseEntity.badRequest().body(api);
        }

        

        try {


            // dang ky google
            Taikhoan tk = new Taikhoan();
            tk.setUsername(generateUsernameByGG(entity.getName()));
            tk.setGoogleId(entity.getSub());
            tk.setEmail(entity.getEmail());
            tk.setHovaten(entity.getName());
            tk.setStatus(entity.isVerified_email() ? 1 : 0);
            tk.setNgaytao(new Date());
            tk.setUrl(entity.getPicture());

            Vaitro vt = vaiTroRepository.findBytenrole(RoleName.CUSTOMER.toString());
            List<Vaitro> vaitros = new ArrayList<>();
            vaitros.add(vt);
            Set<Vaitro> setVaitros = new HashSet<>();
            for (Vaitro vaitro : vaitros) {
                setVaitros.add(vaitro);
            }
            tk.setVaitro(setVaitros);
            taiKhoanService.saveTaiKhoan(tk);
            api.setSuccess(true);
            api.setMessage("Đăng ký thành công với tài khoản Google");
            Map<String, Object> map = new HashMap<>();
            Map<String, Object> roles = new HashMap<>();
            map.put("token", jwtUtil.generateToken(tk.getUsername()));
            // co the luu token vao dtb
            for (Vaitro vtro : tk.getVaitro()) {
                roles.put(String.valueOf(vtro.getVaitroId()), vtro.getTenrole());
            }

            map.put("roles", roles);
            map.put("fullname", tk.getHovaten());
            api.setData(map);
            return ResponseEntity.ok().body(api);
        } catch (Exception e) {
            // TODO: handle exception
        }
        // dang nhap google

        api.setSuccess(false);
        api.setMessage("Có lỗi không mong muốn xảy ra");
        return ResponseEntity.badRequest().body(api);

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
            api.setMessage("Không được để trống dữ liệu");
            api.setData(errorMessage);
            return ResponseEntity.badRequest().body(api);
        }
        Bucket bucket = rateLimitingService.resolveBucket(userRegister.getEmail());
        Bucket bucket2 = rateLimitingService.resolveBucket(userRegister.getUsername());
        Bucket bucket3 = rateLimitingService.resolveBucket(userRegister.getSdt());
        if (!bucket.tryConsume(1) || !bucket2.tryConsume(1) || !bucket3.tryConsume(1)) {
            api.setSuccess(false);
            api.setMessage("Quá nhiều yêu cầu. Vui lòng thử lại sau");
            api.setData(null);
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(api);
        }
        try {
            Taikhoan tk_chuaxacnhan = taiKhoanService.findByEmail(userRegister.getEmail().trim());
            Set<String> err_mes = new HashSet<>();
            if (taiKhoanRepository.existsByUsername(generateUsername(userRegister.getUsername()))) {
                err_mes.add("Username đã tồn tại");
                // api.setSuccess(false);
                // api.setData(null);
                // api.setMessage("Username ton tai");
                // gui ma xac nhan neu tk chua kich hoat

                // return ResponseEntity.badRequest().body(api);
            }
            // kiem tra email
            if (taiKhoanService.existsByEmail(userRegister.getEmail().trim())) {
                err_mes.add("Email đã tồn tại");
                // api.setSuccess(false);
                // api.setMessage("Email da ton tai");
                // api.setData(null);
                // gui ma xac nhan neu tk chua kich hoat
                if (taiKhoanService.findByEmail(userRegister.getEmail()) != null) {
                    if(tk_chuaxacnhan.getStatus() == 0){
                        emailService.sendVerificationEmail(userRegister.getEmail());
                        api.setSuccess(true);
                        api.setMessage("Đã gửi mã xác thực");
                        return ResponseEntity.ok().body(api);
                    }
                }
                // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(api);
            }

            // kiem tra sdt
            if (taiKhoanService.existsBySdt(userRegister.getSdt().trim())) {
                err_mes.add("SDT đã tồn tại");
                // api.setSuccess(false);
                // api.setMessage("Sdt da ton tai");
                // api.setData(null);
                // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(api);

            }
            if (!err_mes.isEmpty()) {
                api.setMessage("Lỗi khi đăng ký tài khoản");
                api.setData(err_mes);
                return ResponseEntity.badRequest().body(api);
            }
            Taikhoan tk = new Taikhoan();
            tk.setUsername(generateUsername(userRegister.getUsername()));
            tk.setPassword(passwordEncoder.encode(userRegister.getPassword()));
            Vaitro vt = vaiTroRepository.findBytenrole(RoleName.CUSTOMER.toString());
            // Vaitro vt2 = vaiTroRepository.findBytenrole(RoleName.AUTHOR.toString());
            // tra ve loi neu ko tim thay vai tro
            List<Vaitro> vaitros = new ArrayList<>();
            vaitros.add(vt);
            // vaitros.add(vt2);
            if (vt == null) {
                api.setSuccess(false);
                api.setMessage("Có lỗi khi tạo vai trò");
                api.setData(null);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(api);
            }
            Set<Vaitro> setVaitros = new HashSet<>();
            for (Vaitro vaitro : vaitros) {
                setVaitros.add(vaitro);
            }
            tk.setVaitro(setVaitros);

            // tai khoan

            tk.setHovaten(userRegister.getHovaten());
            tk.setEmail(userRegister.getEmail());
            tk.setNgaytao(new Date());
            tk.setSdt(userRegister.getSdt());
            tk.setUrl(userRegister.getUrl());
            tk.setStatus(0);
            try {
                taiKhoanService.saveTaiKhoan(tk);
                api.setSuccess(true);
                api.setMessage("Đăng ký thành công vui lòng kiểm tra email để xác thực tài khoản");
                api.setData(null);
                emailService.sendVerificationEmail(tk.getEmail());
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
    public ResponseEntity<ApiResponse<?>> forgotPassword(@RequestParam(required = false) String email) {
        try {
            if (email == null) {
                return ResponseEntity.badRequest().body(null);
            }
            ApiResponse<?> api = new ApiResponse<>();
            // Lấy bucket dựa trên địa chỉ email
            Bucket bucket = rateLimitingService.resolveBucket(email);
            if (!bucket.tryConsume(1)) {
                api.setSuccess(false);
                api.setMessage("Quá nhiều yêu cầu. Vui lòng thử lại sau");
                api.setData(null);
                return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(api);
            }
            boolean send = taikhoanTokenService.createPasswordResetTokenForUser(email);
            if (send == false) {
                api.setSuccess(false);
                api.setMessage("Hãy đảm bảo rằng bạn nhập đúng email cần khôi phục");
                api.setData(null);
                return ResponseEntity.ok().body(api);
            }
            api.setSuccess(true);
            api.setMessage("Thành công! Vui lòng kiểm tra email");
            api.setData(null);
            return ResponseEntity.ok().body(api);
        } catch (Exception e) {
            // TODO: handle exception
            return ResponseEntity.badRequest().body(null);
        }

    }

    // doi mk
    @PostMapping("/reset")
    public ResponseEntity<ApiResponse<?>> resetPassword(@RequestBody(required = false) ResetPassword enity) {
        try {
            if (enity == null || enity.getToken() == null || enity.getNewpassword() == null) {
                return ResponseEntity.badRequest().body(null);
            }
            ApiResponse<?> api = new ApiResponse<>();
            if (taikhoanTokenService.changePassword(enity.getToken(), enity.getNewpassword())) {

                api.setSuccess(true);
                api.setMessage("Doi mk thanh cong");
                api.setData(null);
                return ResponseEntity.ok().body(api);
            }
            api.setSuccess(false);
            api.setMessage("Token het han hoac sai");
            api.setData(null);
            //
            return ResponseEntity.badRequest().body(api);
        } catch (Exception e) {
            // TODO: handle exception
            return ResponseEntity.badRequest().body(null);
        }

    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyEmail(@RequestParam(required = false) String code) {
        if (code == null) {
            return ResponseEntity.badRequest().body(null);
        }
        ApiResponse<?> api = new ApiResponse<>();
        boolean isVerified = emailService.verifyEmail(code);
        if (isVerified) {
            api.setSuccess(true);
            api.setMessage("Xác thực thành công");
            return ResponseEntity.ok().body(api);
        }
        api.setSuccess(false);
        api.setMessage("Mã xác thực không hợp lệ hoặc đã hết hạn");
        return ResponseEntity.badRequest().body(api);
    }

    // xoa nguoi dung
    @PostMapping("/delete")
    public ResponseEntity<ApiResponse<?>> deleteUser(@RequestParam(required = false) String username) {
        // TODO: process POST request
        if (username == null) {
            System.out.println("null tai khoan delete");
            return ResponseEntity.badRequest().body(null);
        }
        ApiResponse<?> api = new ApiResponse<>();
        Taikhoan tk = taiKhoanService.getTaikhoanLogin(username);
        if (tk == null) {
            api.setSuccess(false);
            api.setMessage("Ko có tài khoản này bạn đã chắc chưa");
            return ResponseEntity.badRequest().body(null);
        }
        taiKhoanService.deleteTaiKhoan(tk);
        api.setSuccess(true);
        api.setMessage("Xóa thành công Zangnquuuuuuuuu");
        api.setData("Anh bảo no 1");
        return ResponseEntity.ok().body(api);
    }

}
