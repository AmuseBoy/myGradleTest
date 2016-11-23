package com.liu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableAutoConfiguration
public class GradleApplication {

	//这是变更33
	public static void main(String[] args) {
		SpringApplication.run(GradleApplication.class, args);
	}
}
