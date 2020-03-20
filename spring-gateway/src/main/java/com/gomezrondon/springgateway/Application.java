package com.gomezrondon.springgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.http.server.reactive.ServerHttpRequest;

@SpringBootApplication
@EnableEurekaClient
@EnableCircuitBreaker
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}


	@Bean
	public RouteLocator myRoutes(RouteLocatorBuilder builder) {
		return builder.routes()
				// no se puede mapear /** de primero porque ignora a las demas direcciones
				.route("feign-service",r -> r.path("/v2/**")
						.filters(f -> f.stripPrefix(1)) // remueve el primer segmento /v2
						.uri("lb://feign-car-service"))
				.route("car-jpa-rest",r -> r.path("/**")
						.uri("lb://car-service"))
				.build();
	}
}
