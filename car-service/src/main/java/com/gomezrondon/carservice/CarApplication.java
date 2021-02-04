package com.gomezrondon.carservice;


import com.gomezrondon.carservice.entities.Car;
import com.gomezrondon.carservice.repositories.CarRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import java.util.stream.Stream;

@SpringBootApplication
@EnableEurekaClient
public class CarApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarApplication.class, args);
	}

	@Bean
	ApplicationRunner init(CarRepository repository) {
	//	return args -> {};

		return args -> {
			Stream.of("Ferrari", "Jaguar", "Porsche", "Lamborghini", "Bugatti",
					"AMC Gremlin", "Triumph Stag", "Ford Pinto", "Yugo GV").map(String::trim).map(Car::new).forEach(repository::save);
			repository.findAll().forEach(System.out::println);

		};
	}

}