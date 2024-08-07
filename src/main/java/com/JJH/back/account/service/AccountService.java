package com.JJH.back.account.service;

import com.JJH.back.account.entity.AccountEntity;
import com.JJH.back.account.repository.AccountDTO;
import com.JJH.back.account.repository.AccountRepository;
import com.JJH.back.common.ResponseMessage;
import com.JJH.back.email.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    private final PasswordEncoder passwordEncoder;

    private final EmailService emailService;

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

        return ResponseMessage.builder().statusCode(HttpStatus.OK).message("사용할 수 있는 ID 입니다.").build();
    }


    public ResponseMessage UserLoginService(AccountEntity accountEntity) {
        AccountEntity check = accountRepository.findByUserId(accountEntity.getUserId());

        String inputPassword = accountEntity.getPassword();

        if (check != null) {

            if (passwordEncoder.matches(inputPassword, check.getPassword())) {

                check.setLastConnectedDateTime(LocalDateTime.now());

                accountRepository.save(check);

                return ResponseMessage.builder().statusCode(HttpStatus.OK).message("로그인 성공!").nickName(check.getNickName()).userEmail(check.getEmail()).id(check.getUserId()).tempYn(check.getTempYn()).build();
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다.");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "존재하지 않는 아이디 입니다.");
        }
    }

    public ResponseMessage findUserIdByEmailService(String email) {

        AccountEntity userId = accountRepository.findUserIdByEmail(email);

        if (userId != null) {
            return ResponseMessage.builder()
                    .statusCode(HttpStatus.OK)
                    .message("당신의 아이디는" + userId.getUserId() + "입니다.")
                    .build();
        } else {
            return ResponseMessage.builder()
                    .statusCode(HttpStatus.NOT_FOUND)
                    .message("가입한 이메일이 없습니다.")
                    .build();
        }
    }

    public ResponseMessage findPasswordService(AccountEntity accountEntity) {

        String userId = accountEntity.getUserId();
        String email = accountEntity.getEmail();

        AccountEntity account = accountRepository.findByUserIdAndEmail(userId, email);
        if (account == null) {
            return ResponseMessage.builder()
                    .statusCode(HttpStatus.NOT_FOUND)
                    .message("해당 아이디와 이메일로 가입된 사용자가 없습니다.")
                    .build();
        }

        String tempPassword = UUID.randomUUID().toString().substring(0, 8);

        account.setPassword(passwordEncoder.encode(tempPassword));
        account.setTempYn("y");

        accountRepository.save(account);

        return ResponseEntity.status(HttpStatus.OK).body(emailService.sendEmail(email, tempPassword)).getBody();
    }

    public ResponseMessage changePasswordService(AccountEntity accountEntity) {
        AccountEntity account = accountRepository.findByUserId(accountEntity.getUserId());
        if (account == null) {
            return ResponseMessage.builder()
                    .statusCode(HttpStatus.NOT_FOUND)
                    .message("해당 아이디가 존재하지 않습니다.")
                    .build();
        }

        account.setPassword(passwordEncoder.encode(accountEntity.getPassword()));
        account.setTempYn("n");
        accountRepository.save(account);

        return ResponseMessage.builder()
                .statusCode(HttpStatus.OK)
                .message("비밀번호가 성공적으로 변경되었습니다.")
                .build();
    }

    public ResponseMessage updateNickNameService(AccountEntity accountEntity) {
        AccountEntity account = accountRepository.findByUserId(accountEntity.getUserId());

        if(account == null) {
            return ResponseMessage.builder()
                    .statusCode(HttpStatus.NOT_FOUND)
                    .message("해당 아이디가 존재하지 않습니다.")
                    .build();
        }

        account.setNickName(accountEntity.getNickName());
        accountRepository.save(account);

        return ResponseMessage.builder().statusCode(HttpStatus.OK).message("닉네임 변경완료").nickName(account.getNickName()).build();
    }

    public ResponseMessage updatePassword(AccountDTO accountDTO) {
        AccountEntity account = accountRepository.findByUserId(accountDTO.getUserId());

        if (account != null) {
            if (passwordEncoder.matches(accountDTO.getCurrentPassword(), account.getPassword())) {
                account.setPassword(passwordEncoder.encode(accountDTO.getNewPassword()));
                account.setLastConnectedDateTime(LocalDateTime.now());
                accountRepository.save(account);

                return ResponseMessage.builder().statusCode(HttpStatus.OK).message("비밀번호 변경완료").build();
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다.");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "존재하지 않는 아이디 입니다.");
        }
    }
}
