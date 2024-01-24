package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Otp;
import com.springboot.blog.payload.*;
import com.springboot.blog.repository.OtpRepository;
import com.springboot.blog.utils.AppUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
@Slf4j
public class OtpService {

    private final OtpRepository otpRepository;
    private final  EmailService emailService;

    public Response sendOtp(OtpRequest otpRequest){
        // generate the otp
        // send the otp
        // save the otp
        // check if the user has an existing otp in our system

        Otp existingOtp = otpRepository.findByEmail(otpRequest.getEmail());
        if(existingOtp != null){
            otpRepository.delete(existingOtp);
        }

        String otp = AppUtils.generateOtp();
        log.info("Otp: {}", otp);

        otpRepository.save(Otp.builder()
                        .email(otpRequest.getEmail())
                        .otp(otp)
                        .expiredAt(LocalDateTime.now().plusMinutes(2))

                .build());

        emailService.sendEmail(EmailDetails.builder()
                        .subject("DO NOT DISCLOSE!!!")
                        .recipient(otpRequest.getEmail())
                        .messageBody("This organization has sent you an otp. " +
                                "This otp expires in 2 minutes " + otp)
                .build());

        return Response.builder()
                .statusCode(200)
                .responseMessage("SUCCESS")
                .build();
    }

    public Response validateOtp(OtpValidationRequest otpValidationRequest){
        Otp otp = otpRepository.findByEmail(otpValidationRequest.getEmail());
        log.info("Email: {}", otpValidationRequest.getEmail());
        if(otp == null){

            return Response.builder()
                    .statusCode(400)
                    .responseMessage("You have not sent an otp")
                    .build();
        }
        if(otp.getExpiredAt().isBefore(LocalDateTime.now())){
            return Response.builder()
                    .statusCode(400)
                    .responseMessage("Expired Otp")
                    .build();
        }
        if(!otp.getOtp().equals(otpValidationRequest.getOtp())){

        return Response.builder()
                .statusCode(400)
                .responseMessage("Invalid otp")
                .build();
    }
        return Response.builder()
                .statusCode(200)
                .responseMessage("SUCCESS")
                .otpResponse(OtpResponse.builder()
                        .isOtpValid(true)
                        .build())
                .build();
}   }
