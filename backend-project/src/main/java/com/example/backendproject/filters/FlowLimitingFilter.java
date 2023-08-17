package com.example.backendproject.filters;

import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import com.example.backendproject.entity.RestBean;
import com.example.backendproject.utils.Const;
import com.example.backendproject.utils.FlowUtils;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * 限流控制过滤器
 * 防止用户高频请求接口，借助Redis进行限流
 */
@Component
@Order(Const.ORDER_FLOW_LIMIT)
public class FlowLimitingFilter extends HttpFilter {

    @Resource
    private StringRedisTemplate template;
    // 指定时间内最大请求次数限制
    @Value("${myweb.flow.limit}")
    private int limit;
    // 计数时间周期
    @Value("${myweb.flow.period}")
    private int period;
    // 超出请求限制封禁时间
    @Value("${myweb.flow.block}")
    private int block;

    @Resource
    private FlowUtils utils;

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String address = request.getRemoteAddr();
        if (!tryCount(address))
            this.writeBlockMessage(response);
        else
            chain.doFilter(request, response);
    }

    /**
     * 尝试对指定IP地址请求计数，如果被限制则无法继续访问
     * 
     * @param address 请求IP地址
     * @return 是否操作成功
     */
    private boolean tryCount(String address) {
        synchronized (address.intern()) {
            if (Boolean.TRUE.equals(template.hasKey(Const.FLOW_LIMIT_BLOCK + address)))
                return false;
            String counterKey = Const.FLOW_LIMIT_COUNTER + address;
            String blockKey = Const.FLOW_LIMIT_BLOCK + address;
            return utils.limitPeriodCheck(counterKey, blockKey, block, limit, period);
        }
    }

    /**
     * 为响应编写拦截内容，提示用户操作频繁
     * 
     * @param response 响应
     * @throws IOException 可能的异常
     */
    private void writeBlockMessage(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json;charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.write(RestBean.forbidden("操作频繁，请稍后再试").toJsonStr());
    }
}