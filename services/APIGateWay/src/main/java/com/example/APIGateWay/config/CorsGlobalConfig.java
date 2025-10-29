package com.example.APIGateWay.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.reactive.CorsWebFilter;

@Configuration
public class CorsGlobalConfig {

    private static final Logger log = LoggerFactory.getLogger(CorsGlobalConfig.class);

    @Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration config = new CorsConfiguration();

        // ✅ Cho phép tất cả domain, IP và cổng
        config.addAllowedOriginPattern("*");

        // ✅ Cho phép tất cả header
        config.addAllowedHeader("*");

        // ✅ Cho phép tất cả phương thức (GET, POST, PUT, DELETE, OPTIONS,...)
        config.addAllowedMethod("*");

        // ✅ Cho phép gửi cookie / token nếu cần
        config.setAllowCredentials(true);

        System.out.println("✅ CORS configuration loaded successfully");
        System.out.println("✅ CORS configuration loaded successfully");
        log.info("ket noi thanh cong cors");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsWebFilter(source);
    }
}
