package com.github.hronosf.controllers;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    @Hidden
    @GetMapping("/get_api_token")
    public String getDadataAccessKey(@Value("${dadata.api.key}") String apiKey) {
        return apiKey;
    }
}
