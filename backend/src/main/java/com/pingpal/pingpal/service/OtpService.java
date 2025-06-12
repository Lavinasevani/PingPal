package com.pingpal.pingpal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Random;

@Service
public class OtpService {

    private final HashMap<String, String> otpStorage = new HashMap<>();

    @Autowired
    private EmailService emailService;

    public void generateAndSendOtp(String email) {
        String otp = String.valueOf(new Random().nextInt(900000) + 100000); // 6-digit OTP
        otpStorage.put(email, otp);
        emailService.sendOtpEmail(email, otp);
    }

    public boolean verifyOtp(String email, String userOtp) {
        return otpStorage.containsKey(email) && otpStorage.get(email).equals(userOtp);
    }

    public String resendOtp(String email) {
        generateAndSendOtp(email);
        return email;
    }
}
