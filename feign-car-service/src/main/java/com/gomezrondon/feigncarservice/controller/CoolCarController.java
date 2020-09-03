package com.gomezrondon.feigncarservice.controller;



import com.gomezrondon.feigncarservice.entity.Car;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CoolCarController {

    @Autowired
    private RestTemplate restTemplate;

    private final DiscoveryClient discoveryClient;
    private final TimeServiceProxy timeServiceProxy;
    private final CarClient carClient;

    public CoolCarController(CarClient carClient, DiscoveryClient discoveryClient, TimeServiceProxy timeServiceProxy) {
        this.carClient = carClient;
        this.discoveryClient = discoveryClient;
        this.timeServiceProxy = timeServiceProxy;
    }

    private Collection<Car> fallback() {
        return new ArrayList<>();
    }


    @GetMapping("/feign/time")
    public String getTime() {

        /* exmaple
    "instanceId": "c846d031-eeac-4029-839c-7884fa9af665",
    "serviceId": "car-service",
    "host": "10.1.0.113",
    "port": 8081,
    "uri": "http://10.1.0.113:8081",
    "secure": false,
*/

        List<ServiceInstance> instances = this.discoveryClient.getInstances("car-service");
        ServiceInstance instance = instances.stream().findFirst().orElse(null);
        if (instance != null) {
            URI uri = instance.getUri();
            String url ="".concat(String.valueOf(uri)).concat("/car-service/time");
            System.out.println("url is>> "+url);

            ResponseEntity<String> forEntity = restTemplate.getForEntity(url, String.class);

            System.out.println(forEntity.getBody());
            return forEntity.getBody();
        }

        return "";
    }

    @RequestMapping("/service-instances/{applicationName}")
    public List<ServiceInstance> serviceInstancesByApplicationName(@PathVariable String applicationName) {
        return this.discoveryClient.getInstances(applicationName);
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
