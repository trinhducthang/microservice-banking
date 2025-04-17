package com.ducthang.LoanService.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class GlobalCorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        // Cho phép origin của frontend
                        .allowedOrigins("http://localhost:8888")
                        // Cần có OPTIONS để browser gửi pre‑flight
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        // Cho phép tất cả header cộng thêm explicitly “Authorization”
                        .allowedHeaders("Authorization", "Content-Type", "Accept", "Origin", "X-Requested-With")
                        // Nếu bạn cần đọc header Authorization từ response
                        .exposedHeaders("Authorization")
                        // Cho phép gửi cookie/token
                        .allowCredentials(true);
            }
        };
    }
}
