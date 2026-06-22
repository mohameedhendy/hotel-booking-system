package com.train.hotel_booking_system.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateHotelRequest {

    private String name;
    private String city;
    private String address;
    private String description;
    private Double rating;
}