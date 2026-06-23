package com.train.hotel_booking_system.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Request body for updating hotel details")
public class UpdateHotelRequest {

    @Schema(description = "Hotel name", example = "Demo Hotel Updated")
    @Size(min = 2, max = 100)
    private String name;

    @Schema(description = "Hotel city", example = "Riyadh")
    @Size(min = 2, max = 100)
    private String city;

    @Schema(description = "Hotel address", example = "King Fahd Road")
    @Size(max = 255)
    private String address;

    @Schema(description = "Hotel description", example = "Updated luxury hotel description")
    @Size(max = 1000)
    private String description;

    @Schema(description = "Hotel rating", example = "4.9")
    @DecimalMin(value = "0.0")
    @DecimalMax(value = "5.0")
    private Double rating;
}