package com.be.tapchi.pjtapchi.service;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendActivationEmail(String to, String token, String title) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            // Build the activation link
            String activationLink = token; // assuming 'token' is the full link or use to construct the link

            // Email content in proper string format
            String emailContent =
                    "<html>" +
                            "  <head>" +
                            "    <meta name=\"viewport\" content=\"width=device-width\" />" +
                            "    <meta name=\"description\" content=\"Password Reset - Ar18 - Email Templates for developers\" />" +
                            "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />" +
                            "    <title>Verification Code - As33 - Email Templates for developers</title>" +
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
                            "    </style>" +
                            "  </head>" +
                            "  <body style=\"margin: 0; padding: 0 !important; background: #F8F8F8;\">" +
                            "    <table align=\"center\" valign=\"top\" width=\"100%\" bgcolor=\"#FFFFFF\" style=\"background: #FFFFFF\">" +
                            "      <tr>" +
                            "        <td align=\"center\">" +
                            "          <h1 style=\"font-family: Arial, Helvetica; font-size: 35px; color: #010E28;\">Email verification code</h1>" +
                            "          <p style=\"font-family: Arial, Helvetica; font-size: 14px; color: #5B6987;\">To continue with your email verification, please enter the following code:</p>" +
                            "          <p style=\"font-family: Arial, Helvetica; font-size: 35px; color: #010E28;\">"+token+"</p>" +
                            "          <p style=\"font-family: Arial, Helvetica; font-size: 14px; color: #5B6987;\">Our award-winning customer Dummy Team is available 24/7. If you have any questions, please visit <a href=\"mailto:help@dummy.com\">help@dummy.com</a></p>" +
                            "        </td>" +
                            "      </tr>" +
                            "    </table>" +
                            "  </body>" +
                            "</html>";

            helper.setTo(to);
            helper.setSubject(title);
            helper.setText(emailContent, true); // 'true' indicates it's an HTML email

            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

