package com.example.clouduser.interceptor;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author shs-cyhlwzytxf
 * 通过Feign调用传递Header中参数 http://t.zoukankan.com/cheng2839-p-14715057.html
 */
@Component
public class TokenInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        RequestContextHolder.setRequestAttributes(RequestContextHolder.getRequestAttributes(), true);
        String token = request.getHeader("token");
        if(StringUtils.isNotBlank(token)){
            return true;
        }
        response401(response);
        return false;
    }

    public void response401(HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        JSONObject obj = new JSONObject();
        response.getWriter().print("无token，或token已失效，请重新登录");
    }
}
