package com.deliktas.internshipproject;

import com.deliktas.internshipproject.model.TransactionBanDTO;
import com.deliktas.internshipproject.serializer.TransactionBanDTOSerializer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.LocalDateTime;

@SpringBootApplication
@EnableFeignClients
public class InternshipProjectApplication {
	public static void main(String[] args) {
		SpringApplication.run(InternshipProjectApplication.class, args);
	}


}
