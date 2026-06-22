package com.train.hotel_booking_system.dto;

import com.train.hotel_booking_system.entity.BookingStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Request body for updating booking status")
public class UpdateBookingStatusRequest {

    @Schema(
            description = "New booking status",
            example = "CONFIRMED",
            allowableValues = {"PENDING", "CONFIRMED", "CANCELLED", "COMPLETED"}
    )
    @NotNull
    private BookingStatus status;
}