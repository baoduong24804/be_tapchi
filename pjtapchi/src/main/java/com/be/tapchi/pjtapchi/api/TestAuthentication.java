package com.be.tapchi.pjtapchi.api;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.InvalidCsrfTokenException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.be.tapchi.pjtapchi.jwt.JwtUtil;
import com.be.tapchi.pjtapchi.model.Taikhoan;
import com.be.tapchi.pjtapchi.model.Vaitro;
import com.be.tapchi.pjtapchi.modeltest.LoginRequest;
import com.be.tapchi.pjtapchi.repository.TaikhoanTKRepository;
import com.be.tapchi.pjtapchi.repository.VaiTroRepository;
import com.be.tapchi.pjtapchi.service.TaiKhoanService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;

@RestController
public class TestAuthentication {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private TaiKhoanService taiKhoanService;

    @Autowired
    private VaiTroRepository vaiTroService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TaikhoanTKRepository taikhoanTKRepository;

    @ResponseBody
   @GetMapping("users")
   public List<?> getMethodName() {
       return taiKhoanService.getAllTaiKhoans();
   }
   

    @PostMapping("/login")
    public String createAuthenticationToken(@RequestBody LoginRequest loginRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );
        } catch (AuthenticationException e) {
            throw new Exception("Sai username or password", e);
        }

        // Nếu xác thực thành công, tạo JWT
        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());
        return jwtUtil.generateToken(userDetails.getUsername());
    }

    @PostMapping("/register")
    public String registerUser(@RequestBody LoginRequest registerRequest) {
        // Kiểm tra xem username đã tồn tại chưa
        if (taiKhoanService.existsByUsername(registerRequest.getUsername())) {
            return "Username đã tồn tại!";
        }

        // Tạo đối tượng User từ RegisterRequest và mã hóa mật khẩu
        Taikhoan user = new Taikhoan();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        Set<Vaitro> roles = new HashSet<>();
        Vaitro role = vaiTroService.findBytenrole("USER");
        Vaitro role2 = vaiTroService.findBytenrole("ADMIN");
        roles.add(role);
        roles.add(role2);
       
        user.setVaitro(roles); // Mặc định người dùng mới sẽ có vai trò 'USER'

        // Lưu người dùng vào cơ sở dữ liệu
        taiKhoanService.saveTaiKhoan(user);

        return "Đăng ký thành công!";
    }

    @GetMapping("/token")
    public List<?> getToken() {
        
        return taikhoanTKRepository.findAll();
    }
    

    @GetMapping("/decodeToken")
    public String decodeToken(@RequestParam String token) {
        try {
            
            
            Claims claims = jwtUtil.extractClaims(token);
            if(claims == null){
                return "Token is null";
            }
            String username = claims.getSubject();
            Date expiration = claims.getExpiration();
            // Định dạng ngày giờ
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            String formattedExpiration = sdf.format(expiration);

            // Tính thời gian còn lại trước khi token hết hạn
        Date now = new Date();
        long timeRemainingInMillis = expiration.getTime() - now.getTime();
        
        if (timeRemainingInMillis <= 0) {
            return "Token đã hết hạn.";
        }
        // Chuyển đổi thời gian còn lại thành giờ, phút, giây
        long seconds = (timeRemainingInMillis / 1000) % 60;
        long minutes = (timeRemainingInMillis / (1000 * 60)) % 60;
        long hours = timeRemainingInMillis / (1000 * 60 * 60);


            // Trả về thông tin từ token
            //return String.format("Username: %s, Expiration: %s", username, formattedExpiration);
            // Trả về thông tin từ token cùng với thời gian còn lại
        return String.format("Username: %s, Expiration: %s, Time remaining: %d h %d m %d s",
        username, formattedExpiration, hours, minutes, seconds);
        } catch (InvalidCsrfTokenException e) {
            
            return "Loi token. "+e.getMessage();
        }catch(ExpiredJwtException e){
            return "Token het han. "+e.getMessage();
        }
    }


}


