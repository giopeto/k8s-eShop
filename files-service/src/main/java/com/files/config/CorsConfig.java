package com.files.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableList;

@Configuration
public class CorsConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        List allowedOrigins = asList(
                "http://k8s-eshop.io",
                "http://admin.k8s-eshop.io",
                "http://spring-boot-admin-server.k8s-eshop.io",
                "http://localhost:4200",
                "http://localhost:4008",
                "http://localhost",
                "http://127.0.0.1",
                "http://192.168.50.4"
        );

        configuration.setAllowedOrigins(unmodifiableList(allowedOrigins));
        configuration.setAllowedMethods(unmodifiableList(asList("GET", "POST", "PUT", "DELETE")));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(unmodifiableList(asList("Authorization", "Cache-Control", "Content-Type")));
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
