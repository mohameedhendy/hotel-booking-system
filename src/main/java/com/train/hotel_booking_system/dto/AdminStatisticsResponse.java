package com.train.hotel_booking_system.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Schema(description = "Admin statistics response")
public class AdminStatisticsResponse {

    @Schema(description = "Total number of users", example = "2")
    private long totalUsers;

    @Schema(description = "Total number of hotels", example = "1")
    private long totalHotels;

    @Schema(description = "Total number of rooms", example = "3")
    private long totalRooms;

    @Schema(description = "Total number of available rooms", example = "3")
    private long availableRooms;

    @Schema(description = "Total number of bookings", example = "5")
    private long totalBookings;

    @Schema(description = "Total pending bookings", example = "1")
    private long pendingBookings;

    @Schema(description = "Total confirmed bookings", example = "2")
    private long confirmedBookings;

    @Schema(description = "Total cancelled bookings", example = "1")
    private long cancelledBookings;

    @Schema(description = "Total completed bookings", example = "1")
    private long completedBookings;

    @Schema(description = "Total revenue from confirmed bookings", example = "2500.00")
    private BigDecimal confirmedRevenue;
}