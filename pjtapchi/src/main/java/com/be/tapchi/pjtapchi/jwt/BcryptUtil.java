package com.be.tapchi.pjtapchi.jwt;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BcryptUtil {
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    //BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12); // 12 là độ mạnh

    // Băm dữ liệu
    public static String hash(String input) {
        return encoder.encode(input);
    }

    // Xác minh dữ liệu
    public static boolean verify(String input, String hashed) {
        return encoder.matches(input, hashed);
    }

    public static void main(String[] args) {
        String email = "user@example.com";
        String sub = "123456789";

        // Băm dữ liệu
        String hashedEmail = BcryptUtil.hash(email);
        String hashedSub = BcryptUtil.hash(sub);

        System.out.println("Hashed Email: " + hashedEmail);
        System.out.println("Hashed Sub: " + hashedSub);

        // Kiểm tra chuỗi khớp với bản băm
        boolean isEmailValid = BcryptUtil.verify("ad", hashedEmail);
        boolean isSubValid = BcryptUtil.verify(sub, hashedSub);

        System.out.println("Is Email Valid: " + isEmailValid);
        System.out.println("Is Sub Valid: " + isSubValid);

    }
}
