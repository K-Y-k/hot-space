package com.kyk.HotSpace;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class HotSpaceApplication {

	public static void main(String[] args) {
		SpringApplication.run(HotSpaceApplication.class, args);
	}

}
