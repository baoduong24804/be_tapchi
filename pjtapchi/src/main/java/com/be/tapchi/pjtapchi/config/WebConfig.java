package com.be.tapchi.pjtapchi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;



@Configuration
public class WebConfig implements WebMvcConfigurer {
    //@SuppressWarnings("null")
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Áp dụng cho tất cả các endpoint
                .allowedOrigins("*") // Cho phép tất cả các nguồn
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Các phương thức cho phép
                .allowedHeaders("*"); // Cho phép tất cả các header
    }

    // @Autowired
    // private TokenRefreshInterceptor tokenRefreshInterceptor;
    
    // @Bean
    // public RestTemplate restTemplate() {
    //     RestTemplate restTemplate = new RestTemplate();
    //     restTemplate.getInterceptors().add(tokenRefreshInterceptor);
    //     return restTemplate;
    // }
}
