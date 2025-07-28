package com.kifiya.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class that handles HTTP requests for the home page and cafe information.
 */
@RestController
public class HomeController {

    /**
     * Handles GET requests to the root path "/".
     * Returns a welcome message for the Kifiya Cafe application.
     *
     * @return Welcome message string
     */
    @GetMapping("/")
    public String home() {
        return "Welcome to Kifiya Cafe! Your premier destination for authentic Ethiopian cuisine.";
    }
}
