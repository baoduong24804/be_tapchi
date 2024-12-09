package com.be.tapchi.pjtapchi.service;

import com.be.tapchi.pjtapchi.model.Taikhoan;
import com.be.tapchi.pjtapchi.model.TaikhoanToken;
import com.be.tapchi.pjtapchi.repository.TaikhoanTKRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

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

    @Value("${app.token.expiration:5}")
    private long defaultTokenExpiration;

    public boolean createPasswordResetTokenForUser(String email) {
        try {
            Taikhoan user = taiKhoanService.findByEmail(email);

            if (user == null) {
                return false;
            }

<<<<<<< Updated upstream
          
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
=======

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
>>>>>>> Stashed changes


            LocalDateTime expiryDate = LocalDateTime.now().plusMinutes(defaultTokenExpiration);


            String formattedExpiryDate = expiryDate.format(formatter);
            System.out.println("Đã thêm token có thời hạn đến: " + formattedExpiryDate);

            String token = generateToken();
            TaikhoanToken myToken = new TaikhoanToken();

            if (tokenRepository.findByTaikhoan(user) != null) {
                myToken = tokenRepository.findByTaikhoan(user);
            }
            myToken.setToken(token);
            myToken.setTaikhoan(user);
            // myToken.setExpiryDate(LocalDateTime.now());
            myToken.setExpiryDate(expiryDate);
            // System.out.println(LocalDateTime.now().plusMinutes(0));
            tokenRepository.save(myToken);

            sendResetPasswordEmail(user.getEmail(), token, "Đặt lại mật khẩu",
                    "Để đặt lại mật khẩu của bạn hãy truy cập vào link bên dưới, lưu ý link có thời hạn "+defaultTokenExpiration+" phút. Thời gian hiệu lực đến hết: "+formattedExpiryDate);
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

//        SimpleMailMessage email = new SimpleMailMessage();
//        email.setTo(userEmail);
//        email.setSubject("Reset Password");
//        email.setText("To reset your password, click the link below:\n" + resetUrl +
//                "\nThis link will expire in 15 minutes.");

        emailService.sendActivationEmail(userEmail, resetUrl, title, content);
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

            System.out.println("Doi mk tai khoan: " + passToken.getTaikhoan().getUsername());

            Taikhoan user = passToken.getTaikhoan();
            user.setPassword(passwordEncoder.encode(newPassword));
            taiKhoanService.saveTaiKhoan(user);

            tokenRepository.delete(passToken);
        } catch (Exception e) {
            // TODO: handle exception

            // return false;
            throw e;
        }
        return true;
    }

    public boolean checkExpiryDateFromNowDate(LocalDateTime dateTime) {
        if (dateTime == null) {
            return false;
        }
        try {
            return dateTime.isAfter(LocalDateTime.now());
        } catch (Exception e) {
            // TODO: handle exception
        }
        return false;
    }

    public void deleteTokenExpiryDate() {
        try {
            List<TaikhoanToken> list = tokenRepository.findByExpiryDateBefore(LocalDateTime.now());

            if (list == null || list.size() == 0) {
                System.out.println("Ko co token het han");
                return;
            }

            //List<Taikhoan> listTK = new ArrayList<>();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");


            for (TaikhoanToken tktoken : list) {
                // System.out.println(tktoken.getExpiryDate());
                //listTK.add(tktoken.getTaikhoan());
                String formattedExpiryDate = tktoken.getExpiryDate().format(formatter);
                System.out.println("Tìm thấy token có thời gian hết hạn là: " + formattedExpiryDate);
                tokenRepository.delete(tktoken);
                System.out.println("Đã xóa token hết hạn");
            }

        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("err get taikhantoken expriryDate: " + e.getMessage());
        }

    }

    // Scheduled task để xóa token hết hạn
    @Scheduled(fixedRate = 3600000) // Chạy mỗi giờ
    @Async
    public void checkExpriyDate() {
        // tokenRepository.deleteByExpiryDateLessThan(LocalDateTime.now());// xoa token
        // het han
        try {
            System.out.println("Checkkkk token");

            deleteTokenExpiryDate();

        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Err function checkExpriryDate");
        }

    }
}
