package com.example.backendproject.utils;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

@Component // 用于依赖注入
public class JwtUtils {

    @Value("${jwt.key}") // 读取配置文件中的jwt.key
    String key;

    @Value("${jwt.expire}")
    int expire;

    /**
     * 生成JWT
     * 
     * @param userDetails
     * @return
     */
    public String createJWT(UserDetails user, int id, String username) {
        Algorithm algorithm = Algorithm.HMAC256(key);
        return JWT.create()
                .withClaim("id", id)
                .withClaim("username", username)
                .withClaim("authorities", user.getAuthorities().toString()) // 权限
                .withExpiresAt(this.expireTime())
                .withIssuedAt(new Date())
                .sign(algorithm);

    }

    private Date expireTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, expire * 24);
        return calendar.getTime();
    }

}
