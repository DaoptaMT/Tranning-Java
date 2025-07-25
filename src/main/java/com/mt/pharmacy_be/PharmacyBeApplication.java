package com.mt.pharmacy_be;

import com.mt.pharmacy_be.config.AsyncProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@EnableConfigurationProperties(AsyncProperties.class)
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class PharmacyBeApplication {

	public static void main(String[] args) {
		SpringApplication.run(PharmacyBeApplication.class, args);
	}

}
