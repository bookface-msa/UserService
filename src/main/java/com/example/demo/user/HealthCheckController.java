package com.example.demo.user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/user/health")
public class HealthCheckController {
    
    @GetMapping
    public String health(){
        return "MO5H MA3MLSH SEV!";
    }
}
