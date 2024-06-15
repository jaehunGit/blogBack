package com.JJH.back.review.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity(name = "review")
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ReviewEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", columnDefinition = "VARCHAR(50) comment 'userID'", nullable = true)
    private String userId;

    @Column(columnDefinition = "VARCHAR(50) comment 'nick_name'", nullable = true)
    private String nickName;

    @Column(name = "top", columnDefinition = "VARCHAR(50) comment 'top'", nullable = true)
    private String top;

    @Column(name = "`left`", columnDefinition = "VARCHAR(50) comment 'left'", nullable = true)
    private String left;

    @Column(name = "width", columnDefinition = "VARCHAR(50) comment 'width'", nullable = true)
    private String width;

    @Column(name = "height", columnDefinition = "VARCHAR(50) comment 'height'", nullable = true)
    private String height;

    @Column(name = "rotate", columnDefinition = "DOUBLE comment 'rotate'", nullable = true)
    private Double rotate;

    @Column(name = "text", columnDefinition = "VARCHAR(50) comment 'text'", nullable = true)
    private String text;

    @Column(name = "created_timestamp", columnDefinition = "DATETIME comment '생성시간'", nullable = true)
    private LocalDateTime createdTimestamp;
}
