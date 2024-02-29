package com.aaharan.bookManagement.welcome;

import com.aaharan.bookManagement.auth.login.LoginRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/welcome")
public class Welcome {

    @PostMapping("")
    public String welcomeTest(@RequestBody LoginRequest request) {
        return "Welcome to SCERT..";
    }
}
