package com.sparta.testmyself;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class TestmyselfApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestmyselfApplication.class, args);
	}

}
