package com.example.demo.controller;

import com.example.demo.service.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/otp")
@CrossOrigin(origins = "http://localhost:3000")
public class OtpController {

    @Autowired
    private OtpService otpService;

    @PostMapping("/generate")
    public ResponseEntity<String> generateOtp(@RequestParam String email) {
        String otp = otpService.generateOtp(email);
        System.out.println("Generated OTP for " + email + " : " + otp); 
        return ResponseEntity.ok("OTP sent successfully!");
    }

    @PostMapping("/verify")
    public ResponseEntity<String> verifyOtp(@RequestParam String email, @RequestParam String otp) {
        boolean isValid = otpService.validateOtp(email, otp);
        if (isValid) {
            return ResponseEntity.ok("OTP Verified Successfully!");
        }
        return ResponseEntity.badRequest().body("Invalid or Expired OTP!");
    }
}