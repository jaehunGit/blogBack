package com.JJH.back.account.service;

import com.JJH.back.account.entity.AccountEntity;
import com.JJH.back.account.repository.AccountRepository;
import com.JJH.back.common.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    private final PasswordEncoder passwordEncoder;

    public ResponseMessage signUpUserService(AccountEntity accountEntity) {
        AccountEntity check = accountRepository.findByUserId(accountEntity.getUserId());

        String encodedPassword = passwordEncoder.encode(accountEntity.getPassword());

        if (check == null) {
            check = AccountEntity.builder()
                    .userId(accountEntity.getUserId())
                    .password(encodedPassword)
                    .email(accountEntity.getEmail())
                    .nickName(accountEntity.getNickName())
                    .snsType("none")
                    .lastConnectedDateTime(LocalDateTime.now())
                    .build();
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "중복된 ID 입니다.");
        }

        accountRepository.save(check);

        return ResponseMessage.builder().statusCode(HttpStatus.OK).message("회원가입 성공!").build();

    }

    public ResponseMessage UserIdConfirmService(String userId) {
        AccountEntity check = accountRepository.findByUserId(userId);

        if (check != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "중복된 ID 입니다");
        }

        return ResponseMessage.builder().statusCode(HttpStatus.OK).message("사용할 수 있는 ID입니다.").build();
    }


    public ResponseMessage UserLoginService(AccountEntity accountEntity) {
        AccountEntity check = accountRepository.findByUserId(accountEntity.getUserId());

        String inputPassword = accountEntity.getPassword();

        if (check != null) {

            if (passwordEncoder.matches(inputPassword, check.getPassword())) {

                check.setLastConnectedDateTime(LocalDateTime.now());

                accountRepository.save(check);

                return ResponseMessage.builder().statusCode(HttpStatus.OK).message("로그인 성공!").nickName(check.getNickName()).userEmail(check.getEmail()).build();
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다.");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "존재하지 않는 아이디 입니다.");
        }
    }
}
