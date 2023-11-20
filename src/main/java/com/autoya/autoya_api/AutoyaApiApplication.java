package com.autoya.autoya_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main class for launching the Autoya API application.
 * This class is annotated with {@link SpringBootApplication} to enable Spring Boot auto-configuration.
 */
@SpringBootApplication
public class AutoyaApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(AutoyaApiApplication.class, args);
	}

}
