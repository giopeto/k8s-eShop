package com.store.config;

import com.store.common.AppUtils;
import com.store.common.SecurityInterceptor;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@TestConfiguration
public class TestConfig {

    @MockBean
    private RestTemplate restTemplate;
    @MockBean
    private AppUtils appUtils;

    @Bean
    public SecurityInterceptor securityInterceptor() {
        return new SecurityInterceptor(appUtils, restTemplate) {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
                return true;
            }
        };
    }
}
