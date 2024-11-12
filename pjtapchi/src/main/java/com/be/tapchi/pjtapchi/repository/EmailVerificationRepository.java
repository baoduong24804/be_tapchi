package com.be.tapchi.pjtapchi.repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;

import com.be.tapchi.pjtapchi.model.EmailVerification;
import com.be.tapchi.pjtapchi.model.Taikhoan;
import com.be.tapchi.pjtapchi.model.TaikhoanToken;

import java.util.List;


public interface EmailVerificationRepository extends JpaRepository<EmailVerification, Long> {
    EmailVerification findByVerificationCode(String verificationCode);
    void deleteByCreatedAtLessThan(LocalDateTime now);
    //List<EmailVerification> findByCreatedAtBefore(LocalDateTime dateTime);
    List<EmailVerification> findByCreatedAtBefore(LocalDateTime createdAt);
    EmailVerification findByTaikhoan(Taikhoan taikhoan);
}
