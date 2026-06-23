package com.train.hotel_booking_system.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Schema(description = "Request body for creating a booking")
public class CreateBookingRequest {

    @Schema(description = "Room ID to book", example = "1")
    @NotNull
    private Long roomId;

    @Schema(description = "Check-in date", example = "2026-07-01")
    @NotNull
    @FutureOrPresent
    private LocalDate checkInDate;

    @Schema(description = "Check-out date", example = "2026-07-05")
    @NotNull
    @Future
    private LocalDate checkOutDate;
}