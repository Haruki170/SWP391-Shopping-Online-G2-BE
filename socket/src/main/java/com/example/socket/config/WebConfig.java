package com.example.socket.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Áp dụng cho tất cả các endpoints
                .allowedOrigins("http://localhost:5173") // Nguồn gốc được phép
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Phương thức được phép
                .allowedHeaders("Authorization", "Content-Type") // Cho phép header `Authorization`
                .allowCredentials(true); // Cho phép gửi cookie nếu cần
    }
}
