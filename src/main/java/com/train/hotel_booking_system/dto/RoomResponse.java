package com.train.hotel_booking_system.dto;

import com.train.hotel_booking_system.entity.RoomType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class RoomResponse {

    private Long id;
    private String roomNumber;
    private RoomType roomType;
    private BigDecimal pricePerNight;
    private Integer capacity;
    private Boolean available;

    private Long hotelId;
    private String hotelName;
}