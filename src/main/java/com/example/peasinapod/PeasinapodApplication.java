package com.example.peasinapod;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.peasinapod.Repository")
public class PeasinapodApplication {

	public static void main(String[] args) {
		SpringApplication.run(PeasinapodApplication.class, args);
	}

}
