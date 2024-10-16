package com.be.tapchi.pjtapchi.service;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendActivationEmail(String to, String token) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            
            String activationLink =  token;
            String emailContent = "<p>Click vào liên kết dưới đây để kích hoạt tài khoản của bạn:</p>"
                                 + "<a href=\"" + activationLink + "\">Kích hoạt tài khoản</a>";

            helper.setTo(to);
            helper.setSubject("Xác nhận đăng ký tài khoản");
            helper.setText(emailContent, true);

            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
