package com.JJH.back.account.controller;

import com.JJH.back.account.entity.AccountEntity;
import com.JJH.back.account.repository.AccountDTO;
import com.JJH.back.account.service.AccountService;
import com.JJH.back.common.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/api/signUpUser")
    public ResponseEntity<ResponseMessage> SignUpUser(@RequestBody AccountEntity accountEntity, Error error) {

        return ResponseEntity.status(HttpStatus.OK).body(accountService.signUpUserService(accountEntity));
    }

    @GetMapping("/api/UserIdConfirm")
    public ResponseEntity<ResponseMessage> UserIdConfirm(@RequestParam("userId") String userId) {
        return ResponseEntity.status(HttpStatus.OK).body(accountService.UserIdConfirmService(userId));
    }

    @PostMapping("/api/Login")
    public ResponseEntity<ResponseMessage> UserLogin(@RequestBody AccountEntity accountEntity) {

        return ResponseEntity.status(HttpStatus.OK).body(accountService.UserLoginService(accountEntity));
    }

    @GetMapping("/api/findId")
    public ResponseEntity<ResponseMessage> findId(@RequestParam("email") String email) {

        ResponseMessage response = accountService.findUserIdByEmailService(email);
        return new ResponseEntity<>(response, response.getStatusCode());
    }

    @PostMapping("/api/findPassword")
    public ResponseEntity<ResponseMessage> findPassword(@RequestBody AccountEntity accountEntity) {

        return ResponseEntity.status(HttpStatus.OK).body(accountService.findPasswordService(accountEntity));
    }

    @PostMapping("/api/changePassword")
    public ResponseEntity<ResponseMessage> changePassword(@RequestBody AccountEntity accountEntity) {

        return ResponseEntity.status(HttpStatus.OK).body(accountService.changePasswordService(accountEntity));
    }

    @PostMapping("/api/updateNickName")
    public ResponseEntity<ResponseMessage> updateNickName(@RequestBody AccountEntity accountEntity) {
        return ResponseEntity.status(HttpStatus.OK).body(accountService.updateNickNameService(accountEntity));
    }

    @PostMapping("/api/updatePassword")
    public  ResponseEntity<ResponseMessage> updatePassword(@RequestBody AccountDTO accountDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(accountService.updatePassword(accountDTO));
    }
}
