package com.isaguler.weatherapi.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Constants {

    public static String WEATHER_STACK_API_BASE_URL;

    public static String API_KEY;

    @Value("${weather-stack.base-url}")
    public void setWeatherStackApiBaseUrl(String apiUrl) {
        Constants.WEATHER_STACK_API_BASE_URL = apiUrl;
    }

    @Value("${weather-stack.api-key}")
    public void setApiKey(String apiKey) {
        API_KEY = apiKey;
    }
}
