package com.train.hotel_booking_system.dto;

import com.train.hotel_booking_system.entity.RoomType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class UpdateRoomRequest {

    private String roomNumber;

    private RoomType roomType;

    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal pricePerNight;

    @Min(1)
    private Integer capacity;

    private Boolean available;
}