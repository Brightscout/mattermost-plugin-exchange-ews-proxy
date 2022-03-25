package com.brightscout.ews;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableRetry
public class EwsApplication {

	public static void main(String[] args) {
		SpringApplication.run(EwsApplication.class, args);
	}
}
