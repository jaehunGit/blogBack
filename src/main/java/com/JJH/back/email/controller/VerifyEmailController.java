package com.JJH.back.email.controller;

import com.JJH.back.account.repository.AccountRepository;
import com.JJH.back.common.ResponseMessage;
import com.JJH.back.email.entity.EmailRequest;
import com.JJH.back.email.service.VerifyEmailService;
import com.JJH.back.redis.utill.RedisUtill;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class VerifyEmailController {

    private final VerifyEmailService verifyEmailService;
    private final RedisUtill redisUtil;


    @GetMapping("/api/checkEmail")
    public ResponseEntity<Map<String, Boolean>> checkEmail(@RequestParam("email") String email) {
        boolean isEmailAvailable = verifyEmailService.isEmailAvailable(email);
        Map<String, Boolean> response = new HashMap<>();
        response.put("isEmailAvailable", isEmailAvailable);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/api/sendEmail")
    public ResponseEntity<ResponseMessage> authEmail(@RequestBody @Valid EmailRequest request) {

        return ResponseEntity.status(HttpStatus.OK).body(verifyEmailService.authEmail(request).getBody());
    }

    @GetMapping("/api/checkAuthCode")
    public ResponseEntity<ResponseMessage> getAuthCode(@RequestParam("requestCode") String requestCode, @RequestParam("email") String email) {

        return ResponseEntity.status(HttpStatus.OK).body(verifyEmailService.emailVerification( requestCode, email ));
    }
}
