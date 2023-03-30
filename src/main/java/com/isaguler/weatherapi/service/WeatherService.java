package com.isaguler.weatherapi.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.isaguler.weatherapi.dto.ExternalWeatherResponse;
import com.isaguler.weatherapi.dto.WeatherDTO;
import com.isaguler.weatherapi.model.Weather;
import com.isaguler.weatherapi.repository.WeatherRepository;
import com.isaguler.weatherapi.util.Constants;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
@CacheConfig(cacheNames = {"weathers"})
public class WeatherService {

    private static final Logger logger = LoggerFactory.getLogger(WeatherService.class);

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final WeatherRepository weatherRepository;

    public WeatherService(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    /*@Value("${weather-stack.base-url}")
    public String WEATHER_STACK_API_BASE_URL;

    @Value("${weather-stack.api-key}")
    public String API_KEY;*/

    @Cacheable(key = "#city")
    public WeatherDTO getWeather(String city) {
        logger.info("getWeather method started for: " + city);

        Optional<Weather> weatherOptional = weatherRepository.findFirstByRequestCityNameOrderByUpdatedTimeDesc(city);

        return weatherOptional.map(weather -> {
           if (weather.getUpdatedTime().isBefore(LocalDateTime.now().minusMinutes(30))) {
               logger.info("weather data is not up-to-date for: " + city);
               return generateCityWeatherFromExternalSource(city);
           }

           logger.info("getting weather data from db for: " + city);
           return WeatherDTO.convert(weather);
        }).orElseGet(() -> generateCityWeatherFromExternalSource(city));

    }

    @CachePut(key = "#city")
    public WeatherDTO generateCityWeatherFromExternalSource(String city) {
        logger.info("generating weather data from weather-stack api for: " + city);
        String url = Constants.WEATHER_STACK_API_BASE_URL + "?access_key=" + Constants.API_KEY + "&query=" + city;

        logger.info("url: " + url);

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);

        try {
            ExternalWeatherResponse weatherResponse =
                    objectMapper.readValue(responseEntity.getBody(), ExternalWeatherResponse.class);

            return WeatherDTO.convert(saveWeather(city, weatherResponse));

        } catch (Exception e) {
            logger.error("generateCityWeatherFromExternalSource exception: " + e.getMessage());

            throw new RuntimeException(e.getMessage());
        }
    }

    @CacheEvict(allEntries = true)
    @PostConstruct
    @Scheduled(fixedRateString = "10000")
    public void clearCache(){
        logger.info("Caches are cleared");
    }

    private Weather saveWeather(String city, ExternalWeatherResponse response) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        Weather weather = new Weather(
                city,
                response.location().name(),
                response.location().country(),
                response.current().temperature(),
                LocalDateTime.now(),
                LocalDateTime.parse(response.location().localtime(), formatter)
        );

        return weatherRepository.save(weather);
    }

}
