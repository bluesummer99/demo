package com.rails;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication // same as @Configuration @EnableAutoConfiguration @ComponentScan
public class ApplicationMain {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(ApplicationMain.class);
		app.run(args);
		
	}
}
