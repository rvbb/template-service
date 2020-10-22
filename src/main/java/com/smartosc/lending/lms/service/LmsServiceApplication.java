package com.smartosc.lending.lms.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class LmsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(LmsServiceApplication.class, args);
	}

}
