package com.ourapp.interview;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SuperAppHomeApplication {

	public static void main(String[] args) {
		SpringApplication.run(SuperAppHomeApplication.class, args);
	}

}
