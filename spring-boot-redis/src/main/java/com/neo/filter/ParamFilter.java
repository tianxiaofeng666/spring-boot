package com.neo.filter;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * 创建过滤器
 */
@Component
public class ParamFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        //post请求，解决getReader() has already been called for this request
        BufferedReader br = req.getReader();
        String str, wholeStr = "";
        while((str = br.readLine()) != null){
            wholeStr += str;
        }
        req.setAttribute("realParam", wholeStr);
        JSONObject paramObj = JSONObject.parseObject(wholeStr);
        String partner = paramObj.getString("partner");
        System.out.println("入参partner: " + partner);
        if(StringUtils.isNotBlank(partner)){
            chain.doFilter(req,res);
        }else{
            response401(res);
        }
    }

    private void response401(HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print("缺少参数");
    }

    @Override
    public void destroy() {

    }
}
