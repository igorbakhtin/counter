package com.bakhtin.counter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@Configuration
@EnableRetry
public class CounterApplication {

	public static void main(String[] args) {
		SpringApplication.run(CounterApplication.class, args);
	}

}
