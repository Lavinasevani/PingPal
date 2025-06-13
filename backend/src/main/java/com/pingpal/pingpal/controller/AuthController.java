package com.pingpal.pingpal.controller;

import com.pingpal.pingpal.dto.*;
import com.pingpal.pingpal.model.User;
import com.pingpal.pingpal.service.OtpService;
import com.pingpal.pingpal.service.UserService;
import com.pingpal.pingpal.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*") // for frontend
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private OtpService otpService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    // ✅ Signup (register user)
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest signupRequest) {
        User existing = userService.findByEmail(signupRequest.getEmail());
        if (existing != null) {
            return ResponseEntity.status(409).body(Map.of("success", false, "message", "Email already registered"));
        }

        signupRequest.setPassword(passwordEncoder.encode(signupRequest.getPassword())); // encode password
        User savedUser = userService.registerUser(signupRequest);

        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Signup successful. Please login."
        ));
    }

    // ✅ Send OTP
    @PostMapping("/send-otp")
    public ResponseEntity<?> sendOtp(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        otpService.generateAndSendOtp(email);
        return ResponseEntity.ok(Map.of("success", true, "message", "OTP sent successfully"));
    }

    // ✅ Verify OTP
    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String otp = request.get("otp");

        boolean valid = otpService.verifyOtp(email, otp);
        if (valid) {
            return ResponseEntity.ok(Map.of("success", true, "message", "OTP verified successfully"));
        } else {
            return ResponseEntity.status(400).body(Map.of("success", false, "message", "Invalid OTP"));
        }
    }

    // ✅ Resend OTP
    @PostMapping("/resend-otp")
    public Map<String, String> resendOtp(@RequestBody SendOtpRequest req) {
        String msg = otpService.resendOtp(String.valueOf(req));
        return Map.of("success", "true", "message", msg);
    }

    // ✅ Login & generate JWT
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        User user = userService.findByEmail(loginRequest.getEmail());

        if (user == null) {
            return ResponseEntity.status(404).body(Map.of("success", false, "message", "User not found"));
        }

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            return ResponseEntity.status(401).body(Map.of("success", false, "message", "Invalid credentials"));
        }

        String token = jwtUtil.generateToken(user.getEmail());

        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Login successful",
                "token", token
        ));
    }

    // ✅ NEW: Get user profile (requires JWT token)
    @GetMapping("/profile")
    public ResponseEntity<?> getUserProfile(@RequestHeader("Authorization") String authHeader) {
        try {
            // Extract token from "Bearer <token>"
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(401).body(Map.of("success", false, "message", "Authorization header missing or invalid"));
            }

            String token = authHeader.substring(7); // Remove "Bearer "
            String email = jwtUtil.extractEmail(token);

//            if (email == null || !jwtUtil.validateToken(token, email)) {
//                return ResponseEntity.status(401).body(Map.of("success", false, "message", "Invalid or expired token"));
//            }

            User user = userService.findByEmail(email);
            if (user == null) {
                return ResponseEntity.status(404).body(Map.of("success", false, "message", "User not found"));
            }

            // Return user profile (without password)
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "user", Map.of(
                            "id", user.getId(),
                            "username", user.getUsername(),
                            "email", user.getEmail()
                    )
            ));

        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("success", false, "message", "Server error: " + e.getMessage()));
        }
    }
}