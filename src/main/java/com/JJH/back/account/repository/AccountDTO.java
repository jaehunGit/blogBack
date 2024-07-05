package com.JJH.back.account.repository;

import lombok.Data;

@Data
public class AccountDTO {
    private String userId;
    private String currentPassword;
    private String newPassword;
}
