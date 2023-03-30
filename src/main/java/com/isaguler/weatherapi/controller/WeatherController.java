package com.isaguler.weatherapi.controller;

import com.isaguler.weatherapi.service.WeatherService;
import com.isaguler.weatherapi.util.ResponseHandler;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @RateLimiter(name = "basic")
    @GetMapping("/v1/{city}")
    public ResponseEntity<Object> getWeather(@PathVariable String city) {
        return ResponseHandler.success(HttpStatus.OK, "success", weatherService.getWeather(city));
    }
}
