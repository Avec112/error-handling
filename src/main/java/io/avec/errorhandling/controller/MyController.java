package io.avec.errorhandling.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyController {

    @GetMapping("/hello")
    public String helloThrowsException() {
        throw new RuntimeException("something is wrong!");
    }

}
