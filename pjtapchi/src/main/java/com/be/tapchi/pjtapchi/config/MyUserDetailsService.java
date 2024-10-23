package com.be.tapchi.pjtapchi.config;


import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.be.tapchi.pjtapchi.model.Taikhoan;
import com.be.tapchi.pjtapchi.repository.TaiKhoanRepository;
import com.be.tapchi.pjtapchi.service.TaiKhoanService;



@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private TaiKhoanService taikhoanService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Taikhoan user = taikhoanService.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        
        return new org.springframework.security.core.userdetails.User(
            user.getUsername(),
            user.getPassword(),
            user.getVaitro().stream()
                .map(role -> new SimpleGrantedAuthority(role.getTenrole()))
                .collect(Collectors.toList())
    );
    }
}

