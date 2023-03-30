package com.isaguler.weatherapi.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class ResponseHandler {

    public ResponseHandler() {
    }

    public static ResponseEntity<Object> success(HttpStatus httpStatus, String message, Object responseObject){
        Map<String, Object> map = new HashMap<>();
        map.put("success", true);
        map.put("timestamp", new Date().getTime());
        map.put("message", message);
        map.put("data", responseObject);
        return new ResponseEntity<>(map, httpStatus);
    }

}
