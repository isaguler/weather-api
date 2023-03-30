package com.isaguler.weatherapi.dto;

public record ExternalWeatherResponse(
        Request request,
        Location location,
        Current current
) {
}
