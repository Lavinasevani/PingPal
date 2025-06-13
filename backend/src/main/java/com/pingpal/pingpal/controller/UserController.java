//package com.pingpal.pingpal.controller;
//
////import com.pingpal.pingpal.dto.UserProfileResponse;
//import com.pingpal.pingpal.dto.UserProfileResponse;
//import com.pingpal.pingpal.model.User;
//import com.pingpal.pingpal.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/user")
//public class UserController {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @GetMapping("/profile")
//    public ResponseEntity<UserProfileResponse> getUserProfile(Authentication auth) {
//        String email = auth.getName(); // fetched from JWT
//        User user = userRepository.findByEmail(email);
//
//        if (user == null) {
//            return ResponseEntity.notFound().build();
//        }
//
//        UserProfileResponse profile = new UserProfileResponse(
//                user.getId(),
//                user.getUsername(), // fullName
//                user.getUsername(), // name (same as username for now)
//                user.getEmail()
//        );
//
//        return ResponseEntity.ok(profile);
//    }
//
//
//
//}
