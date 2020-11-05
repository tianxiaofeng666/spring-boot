package com.neo.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 创建拦截器
 */
@Component
public class RedisSessionInterceptor implements HandlerInterceptor {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        String id = request.getParameter("id");
        System.out.println("id: " + id);
        if(session.getAttribute("loginUserId") != null){
            String loginSessionId = stringRedisTemplate.opsForValue().get("loginUser:" + (long) session.getAttribute("loginUserId"));
            if(loginSessionId != null && loginSessionId.equals(session.getId())){
                return true;
            }
        }
        response401(response);
        return false;
    }

    public void response401(HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        JSONObject obj = new JSONObject();
        response.getWriter().print("用户未登录");
    }
}
