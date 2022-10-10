package com.sparta.springhw2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class Springhw2Application {

	public static void main(String[] args) {
		SpringApplication.run(Springhw2Application.class, args);
	}

}
