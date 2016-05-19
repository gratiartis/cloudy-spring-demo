package com.sctrcd.csd.language;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ApplicationContext;

/**
 * The Language API application startup class. This is a Spring Boot
 * micro-service, hence the autoconfig annotations.
 * 
 * @author Stephen Masters
 */
@SpringBootApplication
@EnableEurekaClient
public class LanguageApiApplication {

    private static Logger log = LoggerFactory.getLogger(LanguageApiApplication.class);

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(LanguageApiApplication.class, args);

        String[] activeProfiles = ctx.getEnvironment().getActiveProfiles();
        StringBuilder sb = new StringBuilder("\n\n    Active profiles:\n");
        if (activeProfiles.length == 0) {
            sb.append("        No active profiles.\n");
        } else {
            for (String profile : activeProfiles) {
                sb.append("        " + profile + "\n");
            }
        }

        String[] beanNames = ctx.getBeanDefinitionNames();
        Arrays.sort(beanNames);

        sb.append("\n    Application beans:\n");
        for (String beanName : beanNames) {
            sb.append("        " + beanName + "\n");
        }
        log.info(sb.toString());
    }

}
