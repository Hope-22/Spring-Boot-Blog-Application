package com.springboot.blog.repository;

import com.springboot.blog.entity.Otp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OtpRepository extends JpaRepository<Otp, Long> {

    Otp findByEmail(String email);
}
