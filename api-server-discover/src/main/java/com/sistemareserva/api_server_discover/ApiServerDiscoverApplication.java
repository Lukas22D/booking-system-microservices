package com.sistemareserva.api_server_discover;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class ApiServerDiscoverApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiServerDiscoverApplication.class, args);
	}

}
