package com.JJH.back.account.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@Entity(name = "account")
@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class AccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "VARCHAR(50) comment 'userID'", nullable = true)
    private String userId;

    @Column(columnDefinition = "VARCHAR(255) comment 'password'", nullable = true)
    private String password;

    @Column(columnDefinition = "VARCHAR(50) comment 'nick_name'", nullable = true)
    private String nickName;

    @Column(columnDefinition = "VARCHAR(100) comment 'email'", nullable = true)
    private String email;

    @Column(columnDefinition = "VARCHAR(255) comment 'SNS타입'", nullable = true)
    private String snsType;

    @Column(columnDefinition = "DATETIME comment '로그인 접속시간'", nullable = true)
    private LocalDateTime lastConnectedDateTime;

    @Column(columnDefinition = "임시비밀번호 yn")
    @ColumnDefault("n")
    private String tempYn;
}
