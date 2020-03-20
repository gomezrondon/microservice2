package com.gomezrondon.springgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
@EnableHystrix
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}


	@Bean
	public RouteLocator myRoutes(RouteLocatorBuilder builder) {
		return builder.routes()
				.route(p-> p.path("/time") // esto funciona
						.uri("http://time.jsontest.com/")
				)
				.route(p -> p.path("/all") // no funciona
						.filters(f -> f.addRequestHeader("x-rapidapi-host","ajayakv-rest-countries-v1.p.rapidapi.com")
						.addRequestHeader("x-rapidapi-key", "afacfffb4emsh85bd04fd60dcb05p182285jsn94cc737a7223")
				)
						.uri("https://ajayakv-rest-countries-v1.p.rapidapi.com/rest/v1")
				)
				.route(p -> p.path("/get") // si funciona
						.filters(f -> f.addRequestHeader("hello", "world"))
						.uri("http://httpbin.org:80")
				)
				.build();
	}

}
