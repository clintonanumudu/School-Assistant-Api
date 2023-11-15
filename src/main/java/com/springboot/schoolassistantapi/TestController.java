package com.springboot.schoolassistantapi;

import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@RestController
public class TestController {

    @GetMapping("/test")
    public String test() {
        return "The API is up and running.";
    }
}
