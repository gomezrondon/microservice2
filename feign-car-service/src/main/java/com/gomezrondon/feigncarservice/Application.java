package com.gomezrondon.feigncarservice;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.Data;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@SpringBootApplication
//@EnableEurekaClient
@EnableDiscoveryClient
@EnableFeignClients
@EnableCircuitBreaker
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}



@RestController
class CoolCarController {

	private final CarClient carClient;

	public CoolCarController(CarClient carClient) {
		this.carClient = carClient;
	}

	private Collection<Car> fallback() {
		return new ArrayList<>();
	}

	@GetMapping("/cool-cars")
	@CrossOrigin
	@HystrixCommand(fallbackMethod = "fallback")
	public Collection<Car> goodCars() {
		return carClient.readCars()
				.getContent()
				.stream()
				.filter(this::isCool)
				.collect(Collectors.toList());
	}

	private boolean isCool(Car car) {
		return !car.getName().equals("AMC Gremlin") &&
				!car.getName().equals("Triumph Stag") &&
				!car.getName().equals("Ford Pinto") &&
				!car.getName().equals("Yugo GV");
	}
}

@Data
class Car {
	private String name;
}

@FeignClient(url = "http://car-service:8081", name = "car-service")
interface CarClient {
	@GetMapping("/cars")
	@CrossOrigin
	CollectionModel<Car> readCars();

}