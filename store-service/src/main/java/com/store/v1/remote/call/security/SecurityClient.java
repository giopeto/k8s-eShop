package com.store.v1.remote.call.security;

import com.store.v1.remote.call.security.domain.Users;
import feign.Headers;
import feign.Param;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @deprecated
 */
@Deprecated
@FeignClient(name = "authentication-service-svc")
public interface SecurityClient {

    @GetMapping("/users/v1/users")
    @Headers("Cookie: {sessionId}")
    Users getCurrentAccount(@Param("sessionId") String sessionId);
}
