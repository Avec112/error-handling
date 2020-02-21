package io.avec.errorhandling.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/foo")
public class MyRestController {

    @GetMapping(value = "/bar")
    public String bar() {
        throw new RuntimeException("Oops!");
//        return "foo bar";
    }
}
