package cn.chinaunicom.person.config;

import com.alibaba.druid.support.http.WebStatFilter;

import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

/**
 * @author code-generator
 * @date 2021-5-19 11:09:12
 */
@WebFilter(filterName="druidWebStatFilter",urlPatterns="/*",
    initParams={
        @WebInitParam(name="exclusions",value="*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/monitor/*")//忽略资源
   }
)
public class DruidStatFilter extends WebStatFilter {
 }