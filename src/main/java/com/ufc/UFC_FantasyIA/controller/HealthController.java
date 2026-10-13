package com.ufc.UFC_FantasyIA.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("api/v1/health")
public class HealthController {
    @GetMapping
    public ResponseEntity<Map<String,String>>checkApi(){
        return ResponseEntity.ok(Map.of(

                "status", "healthy",
                "service","API"
                ));
    }
}
