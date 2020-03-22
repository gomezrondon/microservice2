package com.gomezrondon.springgateway;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "welcome")
@Configuration
@Getter
@Setter
public class WelcomeConfiguration {

    private String message = "Welcome Javier...";
}
