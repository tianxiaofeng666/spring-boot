package com.neo.config;

import com.neo.filter.ParamFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.ArrayList;
import java.util.List;

/**
 * 配置过滤器
 */
@Configuration
public class FilterConfig {

    @Autowired
    private ParamFilter paramFilter;

    @Bean
    public FilterRegistrationBean getFilterConfig(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(paramFilter);
        List<String> filterList = new ArrayList<>();
        filterList.add("/test/*");
        filterRegistrationBean.setUrlPatterns(filterList);
        return filterRegistrationBean;
    }
}
