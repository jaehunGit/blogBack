package com.JJH.back.email.service;

import com.JJH.back.common.ResponseMessage;
import com.JJH.back.email.controller.VerifyEmailController;
import com.JJH.back.email.entity.EmailRequest;
import com.JJH.back.redis.utill.RedisUtill;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import java.util.Random;

@Service
@RequiredArgsConstructor
public class VerifyEmailService {

    private final JavaMailSender javaMailSender;
    private final RedisUtill redisUtil;
    private static final Logger logger = LoggerFactory.getLogger(VerifyEmailService.class);

    @Transactional
    public ResponseEntity<ResponseMessage> authEmail(EmailRequest request) {
        Random random = new Random();
        String authKey = String.valueOf(random.nextInt(888888) + 111111);

        return ResponseEntity.status(HttpStatus.OK).body(sendAuthEmail(request.getEmail(), authKey));
    }

    public ResponseMessage sendAuthEmail(String email, String authKey) {
        String subject = "회원가입 인증번호 입니다.";
        String text = "인증번호는 " + authKey + " 입니다. <br/>";

        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "utf-8");
            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(text, true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            logger.error("email 전송에 실패 했습니다.", e);
            return ResponseMessage.builder()
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR)
                    .message("email 전송에 실패 했습니다.")
                    .build();
        }

        redisUtil.setDataExpire(email, authKey, 60 * 5L); // 5분

        return ResponseMessage.builder()
                .statusCode(HttpStatus.OK)
                .message("인증번호를 보냈습니다.")
                .build();
    }


    public ResponseMessage emailVerification( String requestCode, String email ) {

        if ( redisUtil.hasKey(email) && redisUtil.getData(email).equals(requestCode) ) {

            redisUtil.deleteData( email );

            return ResponseMessage.builder().statusCode(HttpStatus.OK).message("인증이 완료되었습니다.").build();
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "인증번호를 정확히 입력해주세요.");
        }
    }
}