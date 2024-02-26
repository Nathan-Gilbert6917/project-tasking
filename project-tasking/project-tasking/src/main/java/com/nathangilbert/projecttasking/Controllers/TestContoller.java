package com.nathangilbert.projecttasking.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestContoller {
    
    @GetMapping("/")
    public String home() {
        return "Hello World";
    }

}
