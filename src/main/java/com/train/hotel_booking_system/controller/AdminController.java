package com.train.hotel_booking_system.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;


@Tag(name = "Admin Dashboard", description = "Simple admin test endpoints")
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Operation(summary = "Admin dashboard", description = "Test endpoint for ADMIN role access")
    @GetMapping("/dashboard")
    public String dashboard() {
        return "Welcome Admin";
    }
}