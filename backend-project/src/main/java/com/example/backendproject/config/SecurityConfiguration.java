package com.example.backendproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import com.example.backendproject.entity.RestBean;

@Configuration
public class SecurityConfiguration {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                // 配置授权，即哪些请求允许匿名访问，哪些请求需要登录
                .authorizeHttpRequests(conf -> conf
                        .requestMatchers("/api/auth/**").permitAll()
                        .anyRequest().authenticated())
                // 配置登录，即登录成功和失败的处理
                .formLogin(conf -> conf
                        .loginProcessingUrl("/api/auth/login")
                        .failureHandler(
                                (req, resp, auth) -> {
                                    resp.setContentType("application/json;charset=utf-8");
                                    resp.getWriter().write(RestBean.failure(401, "用户名或密码错误").toJsonStr());
                                })
                        .successHandler(
                                (req, resp, auth) -> {
                                    resp.setContentType("application/json;charset=utf-8");
                                    resp.getWriter().write(RestBean.success().toJsonStr());
                                }))
                // 配置登出，即退出登录
                .logout(conf -> conf
                        .logoutUrl("/api/auth/logout")
                        .logoutSuccessHandler(
                                (req, resp, auth) -> resp.getWriter().write("logout")))
                // 配置跨域，即允许前端访问后端
                .csrf(AbstractHttpConfigurer::disable)
                // 配置session为无状态，即不使用session
                .sessionManagement(conf -> conf
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }

}
