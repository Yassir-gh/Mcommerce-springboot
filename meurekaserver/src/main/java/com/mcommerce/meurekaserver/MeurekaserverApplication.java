package com.mcommerce.meurekaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class MeurekaserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(MeurekaserverApplication.class, args);
	}

}
