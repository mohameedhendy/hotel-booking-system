package com.train.hotel_booking_system.dto;

import com.train.hotel_booking_system.entity.BookingStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class BookingResponse {

    private Long id;

    private Long userId;
    private String userEmail;

    private Long roomId;
    private String roomNumber;

    private Long hotelId;
    private String hotelName;

    private LocalDate checkInDate;
    private LocalDate checkOutDate;

    private Long nights;
    private BigDecimal totalPrice;

    private BookingStatus status;
}