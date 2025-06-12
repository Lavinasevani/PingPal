package com.pingpal.pingpal.controller;

import com.pingpal.pingpal.dto.LoginRequest;
import com.pingpal.pingpal.dto.OtpRequest;
import com.pingpal.pingpal.dto.SendOtpRequest;
import com.pingpal.pingpal.service.OtpService;
import com.pingpal.pingpal.service.UserService;
import com.pingpal.pingpal.dto.SignupRequest;
import com.pingpal.pingpal.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*") // allow frontend calls
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private OtpService otpService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @PostMapping("/signup")
    public User signup(@RequestBody SignupRequest signupRequest) {
        SignupRequest SignupRequest;
        return userService.registerUser(signupRequest);
    }


    @PostMapping("/send-otp")
    public ResponseEntity<?> sendOtp(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        otpService.generateAndSendOtp(email);
        return ResponseEntity.ok(Map.of("success", true, "message", "OTP sent successfully"));
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String otp = request.get("otp");
        boolean valid = otpService.verifyOtp(email, otp);
        if (valid)
            return ResponseEntity.ok(Map.of("success", true, "message", "OTP verified"));
        else
            return ResponseEntity.status(400).body(Map.of("success", false, "message", "Invalid OTP"));
    }


    @PostMapping("/resend-otp")
    public Map<String, String> resendOtp(@RequestBody SendOtpRequest req) {
        String msg = otpService.resendOtp(String.valueOf(req));
        return Map.of("success", "true", "message", msg);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        User user = userService.findByEmail(loginRequest.getEmail());

        if (user == null) {
            return ResponseEntity.status(404).body(Map.of("success", false, "message", "User not found"));
        }

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            return ResponseEntity.status(401).body(Map.of("success", false, "message", "Invalid credentials"));
        }

        // 💡 You can return JWT token here later
        return ResponseEntity.ok(Map.of("success", true, "message", "Login successful"));
    }


}
