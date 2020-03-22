package com.gomezrondon.springgateway;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class SchedulerComponent {

    @Autowired
    WelcomeConfiguration config;

    @Scheduled(fixedDelay = 5000)
    public void schedulePringMessage() {
        System.out.println(">>*> "+config.getMessage()+ LocalDateTime.now().toString());
    }

}
