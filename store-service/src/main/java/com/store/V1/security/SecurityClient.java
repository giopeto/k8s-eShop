package com.store.V1.security;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "edge-service-svc")
public interface SecurityClient {

    @GetMapping("/api/V1/ratings")
    Users getCurrentAccount(@RequestHeader("Set-Cookie") String sessionId);
}
