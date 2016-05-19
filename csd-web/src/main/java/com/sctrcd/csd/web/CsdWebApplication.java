package com.sctrcd.csd.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * The client application startup class. This is a Spring Boot
 * microservice, hence the autoconfig annotations.
 * 
 * @author Stephen Masters
 */
@SpringBootApplication
@EnableEurekaClient
public class CsdWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(CsdWebApplication.class, args);
    }

    /**
     * Ensures that API calls made via the {@link RestTemplate} will resolve
     * endpoints via Eureka and the Ribbon client-side load balancer.
     * 
     * @return A Eureka-enabled, load balanced {@link RestTemplate}.
     */
    @LoadBalanced
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
