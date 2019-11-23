package com.files.common;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Component
@AllArgsConstructor
public class CookieParser {

    @NonNull
    private final HttpServletRequest request;

    public Optional<String> getCookie(String cookieName) {
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieName)) {
                    return Optional.of(cookie.getValue());
                }
            }
        }

        return Optional.empty();
    }
}
