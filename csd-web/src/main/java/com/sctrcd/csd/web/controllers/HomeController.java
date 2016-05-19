package com.sctrcd.csd.web.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import com.sctrcd.csd.web.domain.Language;

@Controller
public class HomeController {

    private static Logger log = LoggerFactory.getLogger(HomeController.class);

    private final RestTemplate restTemplate;

    @Autowired
    public HomeController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * This will render using the Thymeleaf template:
     * <code>src/main/resources/templates/home/index.html</code>
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    String index() {
        return "home/index";
    }

    @ModelAttribute("languages")
    public List<Language> languages() {
        log.info("Requesting the list of languages...");

        // Use the "smart" Eureka-aware RestTemplate.
        ResponseEntity<List<Language>> exchange =
                this.restTemplate.exchange(
                        "http://language-api/languages",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<Language>>() {
                        },
                        (Object) "language");

        log.info("Returned code [" + exchange.getStatusCode() + "]: ");
        exchange.getBody().forEach(System.out::println);
        return exchange.getBody();
    }

}
