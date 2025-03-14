package com.inventory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class InventoryMicroServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryMicroServiceApplication.class, args);
	}

}
