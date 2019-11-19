package com.store.V1.remote.call.security;

import com.store.V1.remote.call.security.domain.Users;
import feign.Headers;
import feign.Param;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@Deprecated
@FeignClient(name = "edge-service-svc")
public interface SecurityClient {

    @GetMapping("/users/V1/users")
    @Headers("Cookie: {sessionId}")
    Users getCurrentAccount(@Param("sessionId") String sessionId);
}
