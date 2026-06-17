package com.train.hotel_booking_system.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateHotelRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String city;

    private String address;

    private String description;

    private Double rating;
}