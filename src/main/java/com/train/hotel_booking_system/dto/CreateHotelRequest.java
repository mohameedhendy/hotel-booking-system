package com.train.hotel_booking_system.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Request body for creating a hotel")
public class CreateHotelRequest {

    @Schema(description = "Hotel name", example = "Demo Hotel")
    @NotBlank
    private String name;

    @Schema(description = "Hotel city", example = "Riyadh")
    @NotBlank
    private String city;

    @Schema(description = "Hotel address", example = "King Fahd Road")
    private String address;

    @Schema(description = "Hotel description", example = "Luxury hotel in Riyadh")
    private String description;

    @Schema(description = "Hotel rating", example = "4.8")
    private Double rating;
}