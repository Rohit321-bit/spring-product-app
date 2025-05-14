package com.springproj.shop.ShoppingApp.controllers;

import com.springproj.shop.ShoppingApp.advices.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

    @GetMapping(path="/")
    public ResponseEntity<ApiResponse<String>> healthCheckController(){
        ApiResponse<String> apiResponse=new ApiResponse<>("Success!");
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
