package com.train.hotel_booking_system.dto;

import com.train.hotel_booking_system.entity.RoomType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Schema(description = "Response body for room details")
public class RoomResponse {

    @Schema(description = "Room ID", example = "1")
    private Long id;

    @Schema(description = "Room number", example = "101")
    private String roomNumber;

    @Schema(description = "Room type", example = "DOUBLE")
    private RoomType roomType;

    @Schema(description = "Price per night", example = "250.00")
    private BigDecimal pricePerNight;

    @Schema(description = "Room capacity", example = "2")
    private Integer capacity;

    @Schema(description = "Room availability", example = "true")
    private Boolean available;

    @Schema(description = "Hotel ID", example = "1")
    private Long hotelId;

    @Schema(description = "Hotel name", example = "Demo Hotel")
    private String hotelName;
}