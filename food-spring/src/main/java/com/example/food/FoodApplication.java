package com.example.food;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching // Memory Cache
public class FoodApplication {
	public static void main(String[] args) {
		SpringApplication.run(FoodApplication.class, args);
	}

}
