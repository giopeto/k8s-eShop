package com.store.config;

import com.store.common.SecurityInterceptor;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@AllArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    public static final String WILDCARD_PATTERN = "/**";
    public static final String ACTUATOR_PATTERN = "/actuator/**";

    @NonNull
    private final SecurityInterceptor securityInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(securityInterceptor).addPathPatterns(WILDCARD_PATTERN).excludePathPatterns(ACTUATOR_PATTERN);
    }
}
