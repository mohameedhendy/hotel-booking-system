package com.train.hotel_booking_system.dto;

import com.train.hotel_booking_system.entity.BookingStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateBookingStatusRequest {

    @NotNull
    private BookingStatus status;
}