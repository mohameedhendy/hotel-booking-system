package com.train.hotel_booking_system.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Request body for creating a hotel")
public class CreateHotelRequest {

    @Schema(description = "Hotel name", example = "Demo Hotel")
    @NotBlank
    @Size(min = 2, max = 100)
    private String name;

    @Schema(description = "Hotel city", example = "Riyadh")
    @NotBlank
    @Size(min = 2, max = 100)
    private String city;

    @Schema(description = "Hotel address", example = "King Fahd Road")
    @Size(max = 255)
    private String address;

    @Schema(description = "Hotel description", example = "Luxury hotel in Riyadh")
    @Size(max = 1000)
    private String description;

    @Schema(description = "Hotel rating", example = "4.8")
    @DecimalMin(value = "0.0")
    @DecimalMax(value = "5.0")
    private Double rating;
}