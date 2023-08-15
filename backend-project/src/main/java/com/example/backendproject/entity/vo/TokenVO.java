package com.example.backendproject.entity.vo;

import java.util.Date;

import lombok.Data;

@Data
public class TokenVO {
    private String username;
    private String token;
    private Date expire;
    private String role;
}
