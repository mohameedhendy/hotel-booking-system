package com.train.hotel_booking_system.dto;

import com.train.hotel_booking_system.entity.RoomType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Schema(description = "Request body for creating a room")
public class CreateRoomRequest {

    @Schema(description = "Room number", example = "101")
    @NotBlank
    @   Size(min = 1, max = 20)
    private String roomNumber;

    @Schema(
            description = "Room type",
            example = "DOUBLE",
            allowableValues = {"SINGLE", "DOUBLE", "SUITE", "DELUXE"}
    )
    @NotNull
    private RoomType roomType;

    @Schema(description = "Price per night", example = "250.00")
    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal pricePerNight;

    @Schema(description = "Room capacity", example = "2")
    @NotNull
    @Min(1)
    private Integer capacity;

    @Schema(description = "Room availability", example = "true")
    private Boolean available = true;
}