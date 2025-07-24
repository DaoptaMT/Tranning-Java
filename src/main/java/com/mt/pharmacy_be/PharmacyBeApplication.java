package com.mt.pharmacy_be;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class PharmacyBeApplication {

	public static void main(String[] args) {
		SpringApplication.run(PharmacyBeApplication.class, args);
	}

}
