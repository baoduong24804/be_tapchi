package com.be.tapchi.pjtapchi.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.be.tapchi.pjtapchi.model.Taikhoan;
import com.be.tapchi.pjtapchi.model.Vaitro;
import com.be.tapchi.pjtapchi.service.TaiKhoanService;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

    public String refeshToken(Claims claims) {
        try {
            Date expiration = claims.getExpiration();
            long timeRemaining = expiration.getTime() - System.currentTimeMillis();
            long tenMinutesInMillis = 10 * 60 * 1000; // 10 phút tính bằng milliseconds
            //long tenMinutesInMillis = 60 * 60 * 1000 * 10; // hours
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


    public Taikhoan getTaikhoanFromToken(String token){
        try {
            Claims claims = extractClaims(token);
            if (claims == null) {
                return null;
            }
            if(isTokenExpired(token)){
                return null;
            }

            if(!validateToken(token, claims.getSubject())){
                return null;
            }
            Taikhoan tk = taiKhoanService.findByUsername(claims.getSubject());
            if(tk == null){
                return null;
            }
            // tim thay tk
            return tk;

        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("loi khi lay tk tu token: "+e.getMessage());
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

            if(isTokenExpired(token)){
                return false;
            }

            if(!validateToken(token, claims.getSubject())){
                return false;
            }
    
            Taikhoan tk = taiKhoanService.findByUsername(claims.getSubject());
            if(tk == null){
                return false;
            }
            
            Set<String> userRoles = new HashSet<>();

            for (Vaitro vt : tk.getVaitro()) {
                userRoles.add(vt.getTenrole());
            }
    
            // Kiểm tra nếu bất kỳ vai trò nào của user trùng khớp với danh sách roles yêu cầu
            for (String role : roles) {
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
            e.getMessage();
        }
        return null;
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
            Claims claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getExpiration();
        } catch (Exception e) {
            // TODO: handle exception
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
