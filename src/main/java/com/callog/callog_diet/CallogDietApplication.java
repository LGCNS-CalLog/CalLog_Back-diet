package com.callog.callog_diet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

//@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class CallogDietApplication {

	public static void main(String[] args) {
		SpringApplication.run(CallogDietApplication.class, args);
	}

}
