package com.example.backendproject.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.backendproject.entity.dto.Account;

@Mapper
public interface AccountMapper extends BaseMapper<Account> {
    
}
