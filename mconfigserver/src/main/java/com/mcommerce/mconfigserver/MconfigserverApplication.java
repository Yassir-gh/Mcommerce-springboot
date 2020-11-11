package com.mcommerce.mconfigserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableConfigServer
@SpringBootApplication
public class MconfigserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(MconfigserverApplication.class, args);
	}

}
