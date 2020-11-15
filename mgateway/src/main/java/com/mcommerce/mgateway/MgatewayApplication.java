package com.mcommerce.mgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;

@SpringBootApplication
@EnableDiscoveryClient
public class MgatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(MgatewayApplication.class, args);
	}
	
	@Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("microservice-produits_1", r -> r.path("/Produits")
                        .uri("lb://microservice-produits"))
                .route("microservice-produits_2", r -> r.path("/Produits/{id}")
                        .uri("lb://microservice-produits"))
                .route("microservice-commandes_1", r -> r.method(HttpMethod.POST).and().path("/commandes")
                        .uri("lb://microservice-commandes"))
                .route("microservice-commandes_2", r -> r.path("/commandes/{id}")
                        .uri("lb://microservice-commandes"))
                .route("microservice-paiements", r -> r.method(HttpMethod.POST).and().path("/paiement")
                        .uri("lb://microservice-paiements"))
                .build();
    }

}
