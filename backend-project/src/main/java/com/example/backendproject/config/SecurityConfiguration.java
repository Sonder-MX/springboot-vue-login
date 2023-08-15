package com.example.backendproject.config;

import java.io.IOException;
import java.io.PrintWriter;

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
import com.example.backendproject.entity.vo.TokenVO;
import com.example.backendproject.filters.JwtAuthenticationFilter;
import com.example.backendproject.utils.JwtUtils;

import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class SecurityConfiguration {

    @Resource
    private JwtUtils jwtUtils;

    @Resource
    private JwtAuthenticationFilter jwtAuthenticationFilter;

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
                .logoutSuccessHandler(this::onLogoutSuccess))
            // 配置跨域，即允许前端访问后端
            .csrf(AbstractHttpConfigurer::disable)
            // 配置session为无状态，即不使用session
            .sessionManagement(conf -> conf.sessionCreationPolicy(
                SessionCreationPolicy.STATELESS))
            // 配置自定义的Jwt过滤器
            .addFilterBefore(jwtAuthenticationFilter, null)
            .build();
    }

    public void onAuthenticationSuccess(HttpServletRequest request,
            HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        User user = (User) authentication.getPrincipal();
        String token = jwtUtils.createJWT(user, 1, "小米");
        TokenVO tokenVO = new TokenVO();
        tokenVO.setToken(token);
        tokenVO.setUsername("小米");
        tokenVO.setExpire(jwtUtils.expireTime());
        tokenVO.setRole("");
        response.getWriter().write(RestBean.success(tokenVO).toJsonStr());
    }

    public void onAuthenticationFailure(HttpServletRequest request,
            HttpServletResponse response, AuthenticationException exception)
                    throws IOException, ServletException {
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write("");
    }
    
    /**
     * 退出登录处理，将对应的Jwt令牌列入黑名单不再使用
     * @param request 请求
     * @param response 响应
     * @param authentication 验证实体
     * @throws IOException 可能的异常
     */
    private void onLogoutSuccess(HttpServletRequest request,
                                 HttpServletResponse response,
                                 Authentication authentication) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        PrintWriter writer = response.getWriter();
        String authorization = request.getHeader("Authorization");
        if(jwtUtils.invalidateJwt(authorization)) {
            writer.write(RestBean.success("退出登录成功").toJsonStr());
            return;
        }
        writer.write(RestBean.failure(400, "退出登录失败").toString());
    }

}
