package com.sanketgauatm.bog;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class BogApplication {
	public static void main(String[] args) {
		SpringApplication.run(BogApplication.class, args);
	}
}
