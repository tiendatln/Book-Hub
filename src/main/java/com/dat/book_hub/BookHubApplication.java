package com.dat.book_hub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class BookHubApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookHubApplication.class, args);
	}

}
