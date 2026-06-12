package com.train.hotel_booking_system.controller;


import com.train.hotel_booking_system.dto.RegisterRequest;
import com.train.hotel_booking_system.dto.UserResponse;
import com.train.hotel_booking_system.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public UserResponse register(
            @Valid @RequestBody RegisterRequest request
    ) {
        return userService.register(request);
    }
}
