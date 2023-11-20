package com.autoya.autoya_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class AutoyaApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(AutoyaApiApplication.class, args);
	}

}
