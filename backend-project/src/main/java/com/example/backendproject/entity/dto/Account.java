package com.example.backendproject.entity.dto;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.backendproject.entity.BaseData;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@TableName("db_account")
public class Account implements BaseData {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String username;
    private String password;
    private String email;
    private String role;
    private Date createTime;
}
