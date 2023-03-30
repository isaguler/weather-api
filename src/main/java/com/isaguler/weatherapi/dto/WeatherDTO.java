package com.isaguler.weatherapi.dto;

import com.isaguler.weatherapi.model.Weather;

public record WeatherDTO(
        String cityName,
        String country,
        Integer temperature
) {
    public static WeatherDTO convert(Weather weather) {
        return new WeatherDTO(
                weather.getCityName(),
                weather.getCountry(),
                weather.getTemperature()
        );
    }
}
