package com.zziri.comment.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping(value = "/api/hello")
    public String Hello() {
        return "com.zziri.comment<br>Hello!!";
    }
}
