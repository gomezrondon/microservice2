package com.gomezrondon.carservice.controller;


import com.gomezrondon.carservice.entities.Car;
import com.gomezrondon.carservice.repositories.CarRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1")
public class CarController {

    private final CarRepository repository;

    public CarController(CarRepository repository) {
        this.repository = repository;
    }


    @GetMapping("/cars")
    public List<Car> getAllCars() {
        return repository.findAll();
    }

}