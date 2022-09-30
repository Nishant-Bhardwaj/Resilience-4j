package com.prepare.throttle.controller;

import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class TestController {

    Logger logger = LogManager.getLogger(TestController.class);

    // https://javatechonline.com/how-to-implement-fault-tolerance-in-microservices-using-resilience4j/

    @RateLimiter(name = "skillLimiter", fallbackMethod = "getSkillFallback")
    @GetMapping(value = "/students/{skill}")
    public ResponseEntity<String> getSkillData(@PathVariable("skill") String skill, @RequestParam("exp") String exp){
        logger.info("Skill: "+ skill+" , Experience: "+ exp);
        return new ResponseEntity<>("Test Response", HttpStatus.OK);
    }

    public ResponseEntity<String> getSkillFallback(RequestNotPermitted exception){
        logger.info("Maximum limit reached for Skill Data API !!");
        return new ResponseEntity<>(
                "Maximum limit reached for Skill Data API !!",
                HttpStatus.TOO_MANY_REQUESTS
                );
    }
}
