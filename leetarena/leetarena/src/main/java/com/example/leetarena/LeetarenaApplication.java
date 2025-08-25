package com.example.leetarena;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LeetarenaApplication {

	public static void main(String[] args) {
		SpringApplication.run(LeetarenaApplication.class, args);
	}

}
