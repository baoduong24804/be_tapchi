package com.be.tapchi.pjtapchi.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.be.tapchi.pjtapchi.controller.apiResponse.ApiResponse;
import com.be.tapchi.pjtapchi.model.EmailVerification;
import com.be.tapchi.pjtapchi.model.Taikhoan;
import com.be.tapchi.pjtapchi.model.TaikhoanToken;
import com.be.tapchi.pjtapchi.repository.TaiKhoanRepository;
import com.be.tapchi.pjtapchi.repository.TaikhoanTKRepository;

@Service
@Transactional
public class TaikhoanTokenService {

    @Autowired
    private TaiKhoanService taiKhoanService;

    @Autowired
    private TaikhoanTKRepository tokenRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${app.base-url}")
    private String baseUrl;

    @Value("${app.token.expiration:15}")
    private long defaultTokenExpiration;

    public boolean createPasswordResetTokenForUser(String email) {
        try {
            Taikhoan user = taiKhoanService.findByEmail(email);

            if (user == null) {
                return false;
            }

            String token = generateToken();
            TaikhoanToken myToken = new TaikhoanToken();
            
            if(tokenRepository.findByTaikhoan(user) != null){
                myToken = tokenRepository.findByTaikhoan(user);
            }
            myToken.setToken(token);
            myToken.setTaikhoan(user);
           // myToken.setExpiryDate(null);
            myToken.setExpiryDate(LocalDateTime.now().plusMinutes(defaultTokenExpiration));
            tokenRepository.save(myToken);

            sendResetPasswordEmail(user.getTaikhoanchitiet().getEmail(), token,"Đặt lại mật khẩu","Để đặt lại mật khẩu của bạn hãy truy cập vào link bên dưới, lưu ý link có thời hạn 15 phút");
        } catch (Exception e) {
            // TODO: handle exception
            return false;
        }

        return true;
    }

    private String generateToken() {
        return UUID.randomUUID().toString();
    }

    private void sendResetPasswordEmail(String userEmail, String token, String title, String content) {
        String resetUrl = baseUrl + "/reset-password?token=" + token;

        // SimpleMailMessage email = new SimpleMailMessage();
        // email.setTo(userEmail);
        // email.setSubject("Reset Password");
        // email.setText("To reset your password, click the link below:\n" + resetUrl +
        //         "\nThis link will expire in 15 minutes.");

        emailService.sendActivationEmail(userEmail,resetUrl,title,content);
    }

    public boolean validatePasswordResetToken(String token) {
        TaikhoanToken passToken = tokenRepository.findByToken(token);

        if (passToken == null) {
            return false;
        }

        if (passToken.getExpiryDate().isAfter(LocalDateTime.now())) {
            tokenRepository.delete(passToken);
            return true;
        }

        return false;
    }

    public boolean changePassword(String token, String newPassword) {
        try {
            TaikhoanToken passToken = tokenRepository.findByToken(token);
        
            if (passToken == null || !validatePasswordResetToken(token)) {
                System.out.println("Token doi mk het han hoac sai");
                return false;
            }

            System.out.println("Doi mk tai khoan: "+passToken.getTaikhoan().getUsername());
            
            Taikhoan user = passToken.getTaikhoan();
            user.setPassword(passwordEncoder.encode(newPassword));
            taiKhoanService.saveTaiKhoan(user);
            
            tokenRepository.delete(passToken);
        } catch (Exception e) {
            // TODO: handle exception
            
            //return false;
            throw  e;
        }
        return true;
    }

    // Scheduled task để xóa token hết hạn
    @Scheduled(fixedRate = 3600000) // Chạy mỗi giờ
    public void removeExpiredTokens() {
        tokenRepository.deleteByExpiryDateLessThan(LocalDateTime.now());
    }
}
