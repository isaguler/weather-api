package com.isaguler.weatherapi.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Weather {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String requestCityName;

    private String cityName;

    private String country;

    private Integer temperature;

    private LocalDateTime updatedTime;

    private LocalDateTime responseTime;

    public Weather() {
    }

    public Weather(String requestCityName, String cityName, String country, Integer temperature, LocalDateTime updatedTime, LocalDateTime responseTime) {
        this.requestCityName = requestCityName;
        this.cityName = cityName;
        this.country = country;
        this.temperature = temperature;
        this.updatedTime = updatedTime;
        this.responseTime = responseTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRequestCityName() {
        return requestCityName;
    }

    public void setRequestCityName(String requestCityName) {
        this.requestCityName = requestCityName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getTemperature() {
        return temperature;
    }

    public void setTemperature(Integer temperature) {
        this.temperature = temperature;
    }

    public LocalDateTime getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(LocalDateTime updatedTime) {
        this.updatedTime = updatedTime;
    }

    public LocalDateTime getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(LocalDateTime responseTime) {
        this.responseTime = responseTime;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "id=" + id +
                ", requestCityName='" + requestCityName + '\'' +
                ", cityName='" + cityName + '\'' +
                ", country='" + country + '\'' +
                ", temperature=" + temperature +
                ", updatedTime=" + updatedTime +
                ", responseTime=" + responseTime +
                '}';
    }
}
