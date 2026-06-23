package com.train.hotel_booking_system.dto;

import com.train.hotel_booking_system.entity.RoomType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Schema(description = "Request body for updating room details")
public class UpdateRoomRequest {

    @Schema(description = "Room number", example = "102")
    @Size(min = 1, max = 20)
    private String roomNumber;

    @Schema(
            description = "Room type",
            example = "SUITE",
            allowableValues = {"SINGLE", "DOUBLE", "SUITE", "DELUXE"}
    )
    private RoomType roomType;

    @Schema(description = "Price per night", example = "300.00")
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal pricePerNight;

    @Schema(description = "Room capacity", example = "3")
    @Min(1)
    private Integer capacity;

    @Schema(description = "Room availability", example = "true")
    private Boolean available;
}