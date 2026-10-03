package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import java.time.Duration;
import java.util.Random;

@Service
public class OtpService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    private static final Duration TTL = Duration.ofMinutes(5);

    public String generateOtp(String email) {
        String otp = String.format("%06d", new Random().nextInt(999999));
        redisTemplate.opsForValue().set(email, otp, TTL);
        return otp;
    }

    public boolean validateOtp(String email, String userInputOtp) {
        String storedOtp = redisTemplate.opsForValue().get(email);
        if (storedOtp != null && storedOtp.equals(userInputOtp)) {
            redisTemplate.delete(email);
            return true;
        }
        return false;
    }
}