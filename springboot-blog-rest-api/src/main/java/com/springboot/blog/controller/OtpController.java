package com.springboot.blog.controller;

import com.springboot.blog.entity.Otp;
import com.springboot.blog.payload.OtpRequest;
import com.springboot.blog.payload.OtpValidationRequest;
import com.springboot.blog.payload.Response;
import com.springboot.blog.service.impl.OtpService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/otp")
@AllArgsConstructor
public class OtpController {

    private final OtpService otpService;


    @PostMapping("sendOtp")
    public Response sendOtp(@RequestBody OtpRequest otpRequest){

        return otpService.sendOtp(otpRequest);
    }

    @PostMapping("validateOtp")
    public Response validateOtp(@RequestBody OtpValidationRequest otpValidationRequest){
        return otpService.validateOtp(otpValidationRequest);
    }
}

