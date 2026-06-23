package com.train.hotel_booking_system.controller;

import com.train.hotel_booking_system.dto.AdminStatisticsResponse;
import com.train.hotel_booking_system.service.AdminStatisticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/statistics")
@Tag(name = "Admin Statistics", description = "Admin APIs for system statistics and dashboard numbers")
@SecurityRequirement(name = "bearerAuth")
public class AdminStatisticsController {

    private final AdminStatisticsService adminStatisticsService;

    public AdminStatisticsController(AdminStatisticsService adminStatisticsService) {
        this.adminStatisticsService = adminStatisticsService;
    }

    @Operation(
            summary = "Get admin statistics",
            description = "Returns dashboard statistics such as users, hotels, rooms, bookings, and confirmed revenue. Admin only."
    )
    @GetMapping
    public AdminStatisticsResponse getStatistics() {
        return adminStatisticsService.getStatistics();
    }
}