package com.be.tapchi.pjtapchi.service;

import com.be.tapchi.pjtapchi.model.EmailVerification;
import com.be.tapchi.pjtapchi.model.Taikhoan;
import com.be.tapchi.pjtapchi.repository.EmailVerificationRepository;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private EmailVerificationRepository emailVerificationRepository;

    @Autowired
    private TaiKhoanService taiKhoanService;

    @Value("${app.base-url}")
    private String baseUrl;

    @Value("${app.token.expiration:15}")
    private long defaultTokenExpiration;

    public void sendVerificationEmail(String email) {
        Taikhoan taikhoan = taiKhoanService.findByEmail(email);
        if (taikhoan != null) {

            String verificode = getVerificationCode();
            if (verificode == null) {
                throw new NullPointerException("Loi khi tao verify code");
            }

            EmailVerification emailVerification = new EmailVerification();
            if (emailVerificationRepository.findByTaikhoan(taikhoan) != null) {
                emailVerification = emailVerificationRepository.findByTaikhoan(taikhoan);
            }
            emailVerification.setTaikhoan(taikhoan);
            emailVerification.setVerificationCode(verificode);
            emailVerification.setCreatedAt(LocalDateTime.now().plusMinutes(defaultTokenExpiration));
            emailVerificationRepository.save(emailVerification);

            // SimpleMailMessage message = new SimpleMailMessage();
            // message.setTo(email);
            // message.setSubject("Mã xác thực tài khoản");
            // message.setText("Mã xác thực của bạn là: " + verificode);
            // mailSender.send(message);

            sendActivationEmail(email, verificode, "Xác thực email",
                    "Đây là mã để xác thực tài khoản của bạn, lưu ý mã xác thực có thời hạn 15 phút");
        }
    }

    public String getVerificationCode() {
        UUID uuid = UUID.randomUUID();
        for (int i = 0; i < 99; i++) {
            String uuidString = uuid.toString().replace("-", "");
            String shortUuid = uuidString.substring(0, 6);
            if (emailVerificationRepository.findByVerificationCode(shortUuid) == null) {
                return shortUuid;
            }
            uuid = UUID.randomUUID();
        }
        return null;

    }

    public boolean verifyEmail(String verificationCode) {
        EmailVerification emailVerification = emailVerificationRepository.findByVerificationCode(verificationCode);
        if (emailVerification != null) {
            LocalDateTime createdAt = emailVerification.getCreatedAt();
            if (createdAt.isAfter(LocalDateTime.now())) {
                // Xóa mã xác thực sau khi xác thực thành công
                emailVerificationRepository.delete(emailVerification);
                Taikhoan tk = emailVerification.getTaikhoan();
                if (tk.getStatus() != 0) {
                    return true;
                }
                tk.setStatus(1);
                taiKhoanService.saveTaiKhoan(tk);
                return true;
            }

        }
        return false;
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteTokenExpiryDate() {
        try {
            List<EmailVerification> list = emailVerificationRepository.findByCreatedAtBefore(LocalDateTime.now());
            //System.out.println("haahhah" + list.size());
            if (list == null || list.size() == 0) {
                System.out.println("Ko co email verify het han");
                return;
            }

            // List<Taikhoan> listTK = new ArrayList<>();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");

            for (EmailVerification tktoken : list) {
                // System.out.println(tktoken.getExpiryDate());
                // listTK.add(tktoken.getTaikhoan());
                String formattedExpiryDate = tktoken.getCreatedAt().format(formatter);
                System.out.println("Tìm thấy email_vefiry có thời gian hết hạn là: " + formattedExpiryDate);
                emailVerificationRepository.delete(tktoken);
                System.out.println("Đã xóa email_vefiry");
                if (tktoken.getTaikhoan() == null) {
                    System.out.println("tai khoan can xac minh email null");
                    return;
                }
                if (tktoken.getTaikhoan().getStatus() == 0) {
                    taiKhoanService.deleteTaiKhoan(tktoken.getTaikhoan());
                    System.out.println("Đã xóa tk hết hạn");
                }

            }

        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("err get email expriryDate: " + e.getMessage());
            try {
                emailVerificationRepository.deleteAll();
                System.out.println("Xoa tat ca email verify");
            } catch (Exception ex) {
                // TODO: handle exception
                System.out.println("Loi xoa tat ca email verify: " + ex.getMessage());
            }


        }

    }

    // Scheduled task để xóa token hết hạn

    @Scheduled(fixedRate = 3600000) // Chạy mỗi giờ
    @Async
    public void removeExpiredTokens() {
        // emailVerificationRepository.deleteByCreatedAtLessThan(LocalDateTime.now());
        deleteTokenExpiryDate();
    }

    public void sendActivationEmail(String to, String token, String title, String content) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            // Build the activation link
            // String activationLink = token; // assuming 'token' is the full link or use to
            // construct the link
            String emailContent = "<html>" +
                    "  <head>" +
                    "    <meta name=\"viewport\" content=\"width=device-width\" />" +
                    "    <meta name=\"description\" content=\"Email Verification\" />" +
                    "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />" +
                    "    <title>" + title + "</title>" +
                    "    <style>" +
                    "      html, body {" +
                    "          margin: 0 auto !important;" +
                    "          padding: 0 !important;" +
                    "          width: 100% !important;" +
                    "          font-family: sans-serif;" +
                    "          line-height: 1.4;" +
                    "          -webkit-font-smoothing: antialiased;" +
                    "          -ms-text-size-adjust: 100%;" +
                    "          -webkit-text-size-adjust: 100%;" +
                    "      }" +
                    "      * {" +
                    "          -ms-text-size-adjust: 100%;" +
                    "      }" +
                    "      table, td {" +
                    "          mso-table-lspace: 0pt !important;" +
                    "          mso-table-rspace: 0pt !important;" +
                    "      }" +
                    "      img {" +
                    "          display: block;" +
                    "          border: none;" +
                    "          max-width: 100%;" +
                    "          -ms-interpolation-mode: bicubic;" +
                    "      }" +
                    "      a {" +
                    "          text-decoration: none;" +
                    "      }" +
                    "      .button {" +
                    "          display: inline-block;" +
                    "          padding: 10px 20px;" +
                    "          font-size: 16px;" +
                    "cursor: pointer;" +
                    "          background-color: #007BFF;" +
                    "          border-radius: 5px;" +
                    "          text-align: center;" +
                    "          text-decoration: none;" +
                    "      }" +
                    "    </style>" +
                    "  </head>" +
                    "  <body style=\"margin: 0; padding: 0 !important; background: #F8F8F8;\">" +
                    "    <table align=\"center\" valign=\"top\" width=\"100%\" bgcolor=\"#FFFFFF\" style=\"background: #FFFFFF\">"
                    +
                    "      <tr>" +
                    "        <td align=\"center\">" +
                    "          <h1 style=\"font-family: Arial, Helvetica; font-size: 35px; color: #010E28;\">" + title
                    + "</h1>" +
                    "          <p style=\"font-family: Arial, Helvetica; font-size: 14px; color: #5B6987;\">" + content
                    + "</p>" +
                    "          <a style=\"color: #212529; font-weight: bold;\" href=\"" + token
                    + "\" class=\"button\">Reset Password</a>" +
                    "          <p style=\"font-family: Arial, Helvetica; font-size: 14px; color: #5B6987;\">Hỗ trợ 24/7, gửi email qua <a style=\"color: #007BFF;\" href=\"mailto:dthaibao2482004@gmail.com\">dthaibao2482004@gmail.com</a></p>"
                    +
                    "        </td>" +
                    "      </tr>" +
                    "    </table>" +
                    "  </body>" +
                    "</html>";
            // Email content in proper string format
            // String emailContent = "<html>" +
            // " <head>" +
            // " <meta name=\"viewport\" content=\"width=device-width\" />" +
            // " <meta name=\"description\" content=\"Email Verification\" />"
            // +
            // " <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />"
            // +
            // " <title>" + title + "</title>" +
            // " <style>" +
            // " html, body {" +
            // " margin: 0 auto !important;" +
            // " padding: 0 !important;" +
            // " width: 100% !important;" +
            // " font-family: sans-serif;" +
            // " line-height: 1.4;" +
            // " -webkit-font-smoothing: antialiased;" +
            // " -ms-text-size-adjust: 100%;" +
            // " -webkit-text-size-adjust: 100%;" +
            // " }" +
            // " * {" +
            // " -ms-text-size-adjust: 100%;" +
            // " }" +
            // " table, td {" +
            // " mso-table-lspace: 0pt !important;" +
            // " mso-table-rspace: 0pt !important;" +
            // " }" +
            // " img {" +
            // " display: block;" +
            // " border: none;" +
            // " max-width: 100%;" +
            // " -ms-interpolation-mode: bicubic;" +
            // " }" +
            // " a {" +
            // " text-decoration: none;" +
            // " }" +
            // " </style>" +
            // " </head>" +
            // " <body style=\"margin: 0; padding: 0 !important; background: #F8F8F8;\">" +
            // " <table align=\"center\" valign=\"top\" width=\"100%\" bgcolor=\"#FFFFFF\"
            // style=\"background: #FFFFFF\">"
            // +
            // " <tr>" +
            // " <td align=\"center\">" +
            // " <h1 style=\"font-family: Arial, Helvetica; font-size: 35px; color:
            // #010E28;\">" + title
            // + "</h1>"
            // +
            // " <p style=\"font-family: Arial, Helvetica; font-size: 14px; color:
            // #5B6987;\">" + content
            // + "</p>"
            // +
            // " <p style=\"font-family: Arial, Helvetica; font-size: 35px; color:
            // #010E28;\">" + token
            // + "</p>" +
            // " <p style=\"font-family: Arial, Helvetica; font-size: 14px; color:
            // #5B6987;\">Hỗ trợ 24/7, gửi email qua <a
            // href=\"mailto:dthaibao2482004@gmail.com\">dthaibao2482004@gmail.com</a></p>"
            // +
            // " </td>" +
            // " </tr>" +
            // " </table>" +
            // " </body>" +
            // "</html>";

            helper.setTo(to);
            helper.setSubject(title);
            helper.setText(emailContent, true); // 'true' indicates it's an HTML email

            mailSender.send(message);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // EmailService.java

    public void sendResetPasswordEmail(String userEmail, String token, String title, String content) {
        String resetUrl = baseUrl + "/reset-password?token=" + token;

        // HTML content with a button and an image
        String emailContent = "<html>" +
                "  <head>" +
                "    <meta name=\"viewport\" content=\"width=device-width\" />" +
                "    <meta name=\"description\" content=\"Email Verification\" />" +
                "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />" +
                "    <title>" + title + "</title>" +
                "    <style>" +
                "      html, body {" +
                "          margin: 0 auto !important;" +
                "          padding: 0 !important;" +
                "          width: 100% !important;" +
                "          font-family: sans-serif;" +
                "          line-height: 1.4;" +
                "          -webkit-font-smoothing: antialiased;" +
                "          -ms-text-size-adjust: 100%;" +
                "          -webkit-text-size-adjust: 100%;" +
                "      }" +
                "      * {" +
                "          -ms-text-size-adjust: 100%;" +
                "      }" +
                "      table, td {" +
                "          mso-table-lspace: 0pt !important;" +
                "          mso-table-rspace: 0pt !important;" +
                "      }" +
                "      img {" +
                "          display: block;" +
                "          border: none;" +
                "          max-width: 100%;" +
                "          -ms-interpolation-mode: bicubic;" +
                "      }" +
                "      a {" +
                "          text-decoration: none;" +
                "      }" +
                "      .button {" +
                "          display: inline-block;" +
                "          padding: 10px 20px;" +
                "          font-size: 16px;" +
                "          color: #ffffff;" +
                "          background-color: #007BFF;" +
                "          border-radius: 5px;" +
                "          text-align: center;" +
                "          text-decoration: none;" +
                "      }" +
                "    </style>" +
                "  </head>" +
                "  <body style=\"margin: 0; padding: 0 !important; background: #F8F8F8;\">" +
                "    <table align=\"center\" valign=\"top\" width=\"100%\" bgcolor=\"#FFFFFF\" style=\"background: #FFFFFF\">" +
                "      <tr>" +
                "        <td align=\"center\">" +
                "          <img src=\"https://cdn.templates.unlayer.com/assets/1676547950700-Asset%201.png\" alt=\"Reset Password\" />" +
                "          <h1 style=\"font-family: Arial, Helvetica; font-size: 35px; color: #010E28;\">" + title + "</h1>" +
                "          <p style=\"font-family: Arial, Helvetica; font-size: 14px; color: #5B6987;\">" + content + "</p>" +
                "          <a href=\"" + resetUrl + "\" class=\"button\">Reset Password</a>" +
                "          <p style=\"font-family: Arial, Helvetica; font-size: 14px; color: #5B6987;\">Hỗ trợ 24/7, gửi email qua <a href=\"mailto:dthaibao2482004@gmail.com\">dthaibao2482004@gmail.com</a></p>" +
                "        </td>" +
                "      </tr>" +
                "    </table>" +
                "  </body>" +
                "</html>";

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(userEmail);
            helper.setSubject(title);
            helper.setText(emailContent, true); // 'true' indicates it's an HTML email

            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
