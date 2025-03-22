package com.inventory.feign;

import com.inventory.config.FeignConfig;

import com.inventory.model.AuthResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "Auth-Service", url = "http://localhost:8010/api/v1/auth/admin/" )
public interface AuthService {

    @GetMapping("/validate-Admin-token")
    public AuthResponse responseForInv(@RequestParam String token);


    @GetMapping("isAdmin")
    public boolean helloAdmin(@RequestHeader("Authorization") String token);
}
