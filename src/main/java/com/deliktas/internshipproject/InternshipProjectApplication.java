package com.deliktas.internshipproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class InternshipProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(InternshipProjectApplication.class, args);
	}

}
