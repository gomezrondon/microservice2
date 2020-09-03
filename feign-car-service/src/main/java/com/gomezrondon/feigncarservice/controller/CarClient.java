package com.gomezrondon.feigncarservice.controller;

import com.gomezrondon.feigncarservice.entity.Car;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("car-service")
public interface CarClient {
    @GetMapping("/cars")
    //@CrossOrigin
    CollectionModel<Car> readCars();

}