package com.JJH.back.email.entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class EmailRequest {

    @Email
    @NotBlank(message = "이메일은 필수")
    private String email;
}
