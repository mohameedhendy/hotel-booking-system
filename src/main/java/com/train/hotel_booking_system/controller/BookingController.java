package com.train.hotel_booking_system.controller;

import com.train.hotel_booking_system.dto.BookingResponse;
import com.train.hotel_booking_system.dto.CreateBookingRequest;
import com.train.hotel_booking_system.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public BookingResponse createBooking(
            @Valid @RequestBody CreateBookingRequest request,
            Authentication authentication
    ) {
        return bookingService.createBooking(authentication.getName(), request);
    }

    @GetMapping("/my")
    public List<BookingResponse> getMyBookings(Authentication authentication) {
        return bookingService.getMyBookings(authentication.getName());
    }

    @PatchMapping("/{bookingId}/cancel")
    public BookingResponse cancelMyBooking(
            @PathVariable Long bookingId,
            Authentication authentication
    ) {
        return bookingService.cancelMyBooking(authentication.getName(), bookingId);
    }
}