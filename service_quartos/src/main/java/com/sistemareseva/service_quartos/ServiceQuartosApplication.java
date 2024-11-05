package com.sistemareseva.service_quartos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
@EnableFeignClients
public class ServiceQuartosApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceQuartosApplication.class, args);
	}

}
