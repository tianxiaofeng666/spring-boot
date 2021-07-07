package com.example.autoconfigure;

import com.example.autoconfigure.properties.UniviewProperties;
import com.example.autoconfigure.service.UniviewService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
@ConditionalOnWebApplication
@EnableConfigurationProperties(UniviewProperties.class)
public class UniviewAutoConfiguration {
    @Resource
    UniviewProperties univiewProperties;

    @Bean
    public UniviewService univiewService(){
        UniviewService univiewService = new UniviewService();
        univiewService.setUniviewProperties(univiewProperties);
        return univiewService;
    }
}
