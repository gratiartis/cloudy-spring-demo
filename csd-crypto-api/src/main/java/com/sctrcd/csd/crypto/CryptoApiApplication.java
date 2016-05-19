package com.sctrcd.csd.crypto;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ApplicationContext;

/**
 * The Crypto API application startup class. This is a Spring Boot
 * micro-service, hence the autoconfig annotations.
 * 
 * @author Stephen Masters
 */
@SpringBootApplication
@EnableEurekaClient
@EnableAutoConfiguration(exclude = { org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration.class })
public class CryptoApiApplication {

    private static Logger log = LoggerFactory.getLogger(CryptoApiApplication.class);

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(CryptoApiApplication.class, args);

        StringBuilder sb = new StringBuilder();

        String[] beanNames = ctx.getBeanDefinitionNames();
        Arrays.sort(beanNames);

        sb.append("\n    Application beans:\n");
        for (String beanName : beanNames) {
            sb.append("        " + beanName + "\n");
        }
        log.info(sb.toString());
    }

}
