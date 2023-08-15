package com.example.backendproject.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import com.baomidou.mybatisplus.extension.service.IService;

import com.example.backendproject.entity.dto.Account;
import com.example.backendproject.entity.vo.ConfirmResetVO;
import com.example.backendproject.entity.vo.EmailRegisterVO;
import com.example.backendproject.entity.vo.EmailResetVO;

public interface AccountService extends IService<Account>, UserDetailsService  {
    Account findAccountByNameOrEmail(String text);
    String registerEmailVerifyCode(String type, String email, String address);
    String registerEmailAccount(EmailRegisterVO info);
    String resetEmailAccountPassword(EmailResetVO info);
    String resetConfirm(ConfirmResetVO info);
}
