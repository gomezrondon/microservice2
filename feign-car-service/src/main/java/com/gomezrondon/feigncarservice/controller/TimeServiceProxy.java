package com.gomezrondon.feigncarservice.controller;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("car-service")
public interface TimeServiceProxy {

    @GetMapping("/car-service/time")
    public String getTime();


}
