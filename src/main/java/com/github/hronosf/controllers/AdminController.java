package com.github.hronosf.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    @ApiIgnore
    @GetMapping("/get_api_token")
    public String getDadataAccessKey(@Value("dadata.api.key") String apiKey) {
        return apiKey;
    }
}
