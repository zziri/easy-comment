package com.zziri.comment.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/crash")
public class CrashController {
    @GetMapping
    public String crash() {
        throw new RuntimeException("Crash Occrued");
    }
}
