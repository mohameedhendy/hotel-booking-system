package com.train.hotel_booking_system.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Request body for updating hotel details")
public class UpdateHotelRequest {

    @Schema(description = "Hotel name", example = "Demo Hotel Updated")
    private String name;

    @Schema(description = "Hotel city", example = "Riyadh")
    private String city;

    @Schema(description = "Hotel address", example = "King Fahd Road")
    private String address;

    @Schema(description = "Hotel description", example = "Updated luxury hotel description")
    private String description;

    @Schema(description = "Hotel rating", example = "4.9")
    private Double rating;
}