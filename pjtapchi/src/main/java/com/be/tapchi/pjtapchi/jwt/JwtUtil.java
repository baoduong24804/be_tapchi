package com.be.tapchi.pjtapchi.jwt;

import com.be.tapchi.pjtapchi.model.Taikhoan;
import com.be.tapchi.pjtapchi.model.Vaitro;
import com.be.tapchi.pjtapchi.service.TaiKhoanService;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long time;

    @Autowired
    private TaiKhoanService taiKhoanService;

    private long getExpirationTime() {
        try {
            return 1000 * 60 * 60 * time;
        } catch (Exception e) {
            // TODO: handle exception
            throw e;
        }
    }

    public boolean checkTokenAndTaiKhoan(String token) {
        if (token == null) {
            return false;
        }
        if ((token + "".trim()).isEmpty()) {
            return false;
        }
        Taikhoan tk = null;
        try {
            tk = getTaikhoanFromToken(token);
            if (tk == null) {
                return false;
            }
        } catch (Exception e) {
            // TODO: handle exception
            return false;
        }

        // tk chua kich hoat or bi khoa
        return tk.getStatus() != 0 && tk.getStatus() != -1;
    }

    public String refeshToken(Claims claims) {
        try {
            Date expiration = claims.getExpiration();
            long timeRemaining = expiration.getTime() - System.currentTimeMillis();
            long tenMinutesInMillis = 10 * 60 * 1000; // 10 phút tính bằng milliseconds
            // long tenMinutesInMillis = 60 * 60 * 1000 * 10; // hours
            if (timeRemaining <= tenMinutesInMillis) {
                String newToken = generateToken(claims.getSubject());
                return newToken;
            }
        } catch (Exception e) {
            // TODO: handle exception
            throw e;
        }
        return null;
    }

    public Map<String, Object> checkTokenConHSD(String token) {
        Map<String, Object> result = new HashMap<>();
        try {

            // Lấy thời gian hết hạn của token từ claims
            Date expirationDate = getExpirationDateFromToken(token);

            // Lấy thời gian hiện tại
            Date currentDate = new Date();

            // Tính thời gian còn lại của token
            long remainingTimeMillis = expirationDate.getTime() - currentDate.getTime();

            // Kiểm tra token đã hết hạn chưa
            if (remainingTimeMillis <= 0) {
                result.put("status", "false"); // Token đã hết hạn
                result.put("message", "Token đã hết hạn.");
                return result;
            }

            // Tính thời gian còn lại theo đơn vị ngày, giờ, phút, giây
            long remainingDays = remainingTimeMillis / (1000 * 60 * 60 * 24);
            long remainingHours = (remainingTimeMillis / (1000 * 60 * 60)) % 24;
            long remainingMinutes = (remainingTimeMillis / (1000 * 60)) % 60;
            long remainingSeconds = (remainingTimeMillis / 1000) % 60;

            // Trả về trạng thái và thông báo về thời gian còn lại
            result.put("status", "true"); // Token còn hiệu lực
            result.put("message", String.format("Token còn hiệu lực: %d ngày, %d giờ, %d phút, %d giây.",
                    remainingDays, remainingHours, remainingMinutes, remainingSeconds));
            return result;

        } catch (Exception e) {
            result.put("status", "false");
            result.put("message", "Token hết hạn: " + e.getMessage());
            return result;
        }
    }

    public Taikhoan getTaikhoanFromToken(String token) {
        try {
            Claims claims = extractClaims(token);
            if (claims == null) {
                return null;
            }
            if (isTokenExpired(token)) {
                return null;
            }

            if (!validateToken(token, claims.getSubject())) {
                return null;
            }
            Taikhoan tk = taiKhoanService.findByUsername(claims.getSubject());
            // tim thay tk
            return tk;

        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("loi khi lay tk tu token: " + e.getMessage());
            return null;
        }

    }

    public boolean checkRolesFromToken(String token, String... roles) {
        try {
            // Giải mã token và lấy thông tin claims
            Claims claims = extractClaims(token);
            if (claims == null) {
                return false;
            }

            if (isTokenExpired(token)) {
                return false;
            }

            if (!validateToken(token, claims.getSubject())) {
                return false;
            }

            Taikhoan tk = taiKhoanService.findByUsername(claims.getSubject());
            if (tk == null) {
                return false;
            }

            Set<String> userRoles = new HashSet<>();

            for (Vaitro vt : tk.getVaitro()) {
//                System.out.println("add: " + vt.getTenrole());
                userRoles.add(vt.getTenrole());
            }

            // Kiểm tra nếu bất kỳ vai trò nào của user trùng khớp với danh sách roles yêu
            // cầu
            for (String role : roles) {
                // System.out.println("check: "+role);
                if (userRoles.contains(role)) {
                    return true;
                }
            }

            // Không có vai trò nào trùng khớp
            return false;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String generateToken(String username) {
        try {
            Map<String, Object> claims = new HashMap<>();
            return Jwts.builder()
                    .setClaims(claims)
                    .setSubject(username)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + getExpirationTime()))
                    .signWith(SignatureAlgorithm.HS512, secretKey)
                    .compact();
        } catch (Exception e) {
            // TODO: handle exception
            throw e;
        }
    }

    public Claims extractClaims(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody();
            return claims;
        } catch (Exception e) {
            // TODO: handle exception
            return null;

        }

    }

    public String extractUsername(String token) {
        try {
            return extractClaims(token).getSubject();
        } catch (Exception e) {
            // TODO: handle exception
            throw e;
        }
    }

    public Date getExpirationDateFromToken(String token) {
        try {
            // Giải mã token và lấy Claims
            Claims claims = Jwts.parser()
                    .setSigningKey(secretKey) // Đảm bảo sử dụng đúng secret key
                    .parseClaimsJws(token) // Parse token để lấy claims
                    .getBody();

            // Trả về ngày hết hạn
            return claims.getExpiration();

        } catch (ExpiredJwtException e) {
            // Token đã hết hạn
            System.out.println("Token đã hết hạn.");
            throw e;
        } catch (MalformedJwtException e) {
            // Token không hợp lệ (định dạng sai)
            System.out.println("Token không hợp lệ.");
            throw e;
        } catch (SignatureException e) {
            // Chữ ký của token không hợp lệ
            System.out.println("Chữ ký của token không hợp lệ.");
            throw e;
        } catch (Exception e) {
            // Lỗi chung khi giải mã token
            System.out.println("Lỗi khi xử lý token: " + e.getMessage());
            throw e;
        }
    }

    public String getUsernameFromToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getSubject();
        } catch (Exception e) {
            // TODO: handle exception
            throw e;
        }
    }

    public boolean isTokenExpired(String token) {
        try {
            return extractClaims(token).getExpiration().before(new Date());
        } catch (Exception e) {
            // TODO: handle exception
            throw e;
        }

    }

    // neu username dung va token chua het han -> true
    public boolean validateToken(String token, String username) {
        try {
            return (username.equals(extractUsername(token)) && !isTokenExpired(token));
        } catch (Exception e) {
            throw e;
            // TODO: handle exception
        }

    }
}
