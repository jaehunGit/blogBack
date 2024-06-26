package com.JJH.back.email.service;

import com.JJH.back.common.ResponseMessage;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.mail.MessagingException;

@Service
@RequiredArgsConstructor
public class EmailService {

    @Autowired
    private final JavaMailSender javaMailSender;

    private static final Logger logger = LoggerFactory.getLogger(VerifyEmailService.class);

    public ResponseMessage sendEmail(String email, String tempPassword) {


        String subject = "임시 비밀번호입니다.";
        String text = "임시 비밀번호는 " + tempPassword + "입니다.";
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
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
        return ResponseMessage.builder()
                .statusCode(HttpStatus.OK)
                .message("임시 비밀번호를 보냈습니다.")
                .build();
    }
}