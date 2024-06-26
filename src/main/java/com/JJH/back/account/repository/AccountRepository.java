package com.JJH.back.account.repository;

import com.JJH.back.account.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Long> {
    AccountEntity findByUserId(String user_id);

    AccountEntity findByEmail(String email);

    AccountEntity findByNickName(String nickName);

    AccountEntity findUserIdByEmail(String email);
    AccountEntity findByUserIdAndEmail(String userId, String email);
}