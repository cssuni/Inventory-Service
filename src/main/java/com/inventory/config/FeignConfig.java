package com.inventory.config;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Configuration
public class FeignConfig {

    @Bean
    public RequestInterceptor requestInterceptor() {

        return requestTemplate -> {
            // Propagate the Authorization header from the current request
            ServletRequestAttributes attributes = (ServletRequestAttributes)
                    RequestContextHolder.getRequestAttributes();
            if (attributes != null) {
                String authorizationHeader = attributes.getRequest()
                        .getHeader(HttpHeaders.AUTHORIZATION);
                requestTemplate.header(HttpHeaders.AUTHORIZATION, authorizationHeader);
            }
        };
    }
}