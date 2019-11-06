package com.store.common;

import com.store.V1.remote.call.security.Users;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

import static com.store.common.StoreConstants.EDGE_SERVICE_GET_CURRENT_ACCOUNT_URL;
import static com.store.common.StoreConstants.JSESSIONID;

@Component
@Slf4j
@RequiredArgsConstructor
public class SecurityInterceptor implements HandlerInterceptor {

    @NonNull
    private final AppUtils appUtils;

    @NonNull
    private final RestTemplate restTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        log.info("[SecurityInterceptor] checking current user ...");
        Optional<String> jSessionIdCookie = appUtils.getCookie(JSESSIONID);

        if (jSessionIdCookie.isPresent()) {
            Users user = getCurrentAccount(jSessionIdCookie.get());
            if (user != null) {
                log.info("[SecurityInterceptor] User is logged: " + user.toString());
                return true;
            }
        }

        log.info("[SecurityInterceptor] checking current user end");

        return false;
    }

    private Users getCurrentAccount(String sessionId) {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Cookie", JSESSIONID + "=" + sessionId);

        return restTemplate.exchange(EDGE_SERVICE_GET_CURRENT_ACCOUNT_URL,
                HttpMethod.GET,
                new HttpEntity<String>(requestHeaders),
                Users.class).getBody();
    }

}
