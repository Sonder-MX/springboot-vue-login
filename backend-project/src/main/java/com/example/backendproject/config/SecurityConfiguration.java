package com.example.backendproject.config;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.SecurityFilterChain;

import com.example.backendproject.entity.RestBean;
import com.example.backendproject.utils.JwtUtils;

import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class SecurityConfiguration {

    @Resource
    private JwtUtils jwtUtils;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                // 配置授权，即哪些请求允许匿名访问，哪些请求需要登录
                .authorizeHttpRequests(conf -> conf.requestMatchers("/api/auth/**")
                        .permitAll().anyRequest().authenticated())
                // 配置登录，即登录成功和失败的处理
                .formLogin(conf -> conf.loginProcessingUrl("/api/auth/login")
                        .failureHandler(this::onAuthenticationFailure)
                        .successHandler(this::onAuthenticationSuccess))
                // 配置登出，即退出登录
                .logout(conf -> conf.logoutUrl("/api/auth/logout")
                        .logoutSuccessHandler((req, resp, auth) -> resp
                                .getWriter().write("logout")))
                // 配置跨域，即允许前端访问后端
                .csrf(AbstractHttpConfigurer::disable)
                // 配置session为无状态，即不使用session
                .sessionManagement(conf -> conf.sessionCreationPolicy(
                        SessionCreationPolicy.STATELESS))
                .build();
    }

    public void onAuthenticationSuccess(HttpServletRequest request,
            HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        User user = (User) authentication.getPrincipal();
        String token = jwtUtils.createJWT(user, 1, "小米");
        response.getWriter().write(RestBean.success(token).toJsonStr());
    }

    public void onAuthenticationFailure(HttpServletRequest request,
            HttpServletResponse response, AuthenticationException exception)
            throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write("");
    }

}
