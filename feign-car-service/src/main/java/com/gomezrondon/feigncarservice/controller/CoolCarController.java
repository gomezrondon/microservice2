package com.gomezrondon.feigncarservice.controller;


import com.gomezrondon.feigncarservice.entities.Car;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@RestController
public class CoolCarController {

    public static final String BASE_URL = "http://car-service/v1/cars";
    private final RestTemplate restTemplate;

    public CoolCarController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private ResponseEntity<Collection<Car>> fallback() {
        return new ResponseEntity<>(Collections.emptyList(), HttpStatus.OK);
    }

    @GetMapping("/cool-cars")
    @CrossOrigin
    @HystrixCommand(fallbackMethod = "fallback")
    public ResponseEntity<Collection<Car>> goodCars() {

      //  Car[] forObject = restTemplate.getForObject(BASE_URL, Car[].class);

        ResponseEntity<Car[]> responseEntity = restTemplate.getForEntity(BASE_URL, Car[].class);
        HttpStatus statusCode = responseEntity.getStatusCode();
        List<Car> collect = Arrays.stream(responseEntity.getBody()).collect(Collectors.toList());

        List<Car> collect1 = collect.stream()
                .filter(this::isCool)
                .collect(Collectors.toList());

        return new ResponseEntity<>(collect1, HttpStatus.OK);
    }

    private boolean isCool(Car car) {
        return !car.getName().equals("AMC Gremlin") &&
                !car.getName().equals("Triumph Stag") &&
                !car.getName().equals("Ford Pinto") &&
                !car.getName().equals("Yugo GV");
    }

}
