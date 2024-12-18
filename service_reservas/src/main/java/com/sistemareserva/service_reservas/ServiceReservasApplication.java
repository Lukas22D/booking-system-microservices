package com.sistemareserva.service_reservas;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;
@EnableAsync
@SpringBootApplication
@EnableRabbit
@EnableFeignClients
public class ServiceReservasApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceReservasApplication.class, args);
	}

}
