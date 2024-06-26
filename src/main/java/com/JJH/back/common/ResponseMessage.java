package com.JJH.back.common;

import lombok.*;
import org.springframework.http.HttpStatus;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ResponseMessage {

    private HttpStatus statusCode;
    private String message;
    private Integer code;
    private String id;
    private String nickName;
    private String userEmail;
    private String tempYn;

}