package com.example.springbootrest.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * springboot解决设置虚拟路径映射绝对路径, 上传linux服务器专用
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${file.docBase}")
    private String docBase;

    @Value("${file.path}")
    private String filePath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(filePath + "**").addResourceLocations("file:" + docBase);
    }
}
