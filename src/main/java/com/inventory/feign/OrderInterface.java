package com.inventory.feign;

import com.inventory.model.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "Order-Service", url = "http://order-service.local:8081/api/v1/Orders/")
public interface OrderInterface {

    @GetMapping("getOrder")
    public ResponseEntity<Order> getOrder(@RequestParam Long orderId);

}
