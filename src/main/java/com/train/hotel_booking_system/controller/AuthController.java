package com.train.hotel_booking_system.controller;

import com.train.hotel_booking_system.dto.LoginRequest;
import com.train.hotel_booking_system.dto.LoginResponse;
import com.train.hotel_booking_system.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        return userService.login(request);
    }

    @GetMapping("/me")
    public String me() {
        return "You are authenticated";
    }
}