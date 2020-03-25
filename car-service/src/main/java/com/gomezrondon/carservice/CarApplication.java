package com.gomezrondon.carservice;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.stream.Stream;

@SpringBootApplication
@EnableEurekaClient
public class CarApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarApplication.class, args);
	}

	@Bean
	ApplicationRunner init(CarRepository repository) {
		return args -> {
			Stream.of("Ferrari", "Jaguar", "Porsche", "Lamborghini", "Bugatti",
					"AMC Gremlin", "Triumph Stag", "Ford Pinto", "Yugo GV").map(String::trim).map(Car::new).forEach(repository::save);
			repository.findAll().forEach(System.out::println);

		};
	}

}

@Data
@NoArgsConstructor
@Entity
class Car {
	@Id
	@GeneratedValue
	private Long id;
	@NonNull
	private String name;

	public Car(String name) {
		this.name = name;
	}

}

@RepositoryRestResource
interface CarRepository extends JpaRepository<Car, Long> {
}