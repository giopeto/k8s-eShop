package com.files.config;

import com.files.common.CookieParser;
import com.files.common.SecurityInterceptor;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@TestConfiguration
@AutoConfigureDataMongo
@TestPropertySource("classpath:test.properties")
public class TestConfig {

    @MockBean
    private RestTemplate restTemplate;
    @MockBean
    private CookieParser cookieParser;
    @MockBean
    private Environment environment;

    @Bean
    public SecurityInterceptor securityInterceptor() {
        return new SecurityInterceptor(cookieParser, restTemplate, environment) {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
                return true;
            }
        };
    }

}
