package com.be.tapchi.pjtapchi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.be.tapchi.pjtapchi.config.MyUserDetailsService;
import com.be.tapchi.pjtapchi.jwt.JwtUtil;

@Service
public class AuthenticationService {
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private MyUserDetailsService userDetailsService;
    
    public String refreshToken(String currentToken) {
        // Lấy thông tin user từ token hiện tại
        String username = jwtUtil.getUsernameFromToken(currentToken);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if(userDetails == null){
            return null;
        }
        
        // Tạo token mới
        return jwtUtil.generateToken(userDetails.getUsername());
    }
}
