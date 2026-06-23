package com.train.hotel_booking_system.controller;

import com.train.hotel_booking_system.dto.BookingResponse;
import com.train.hotel_booking_system.dto.CreateBookingRequest;
import com.train.hotel_booking_system.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;

@Tag(name = "User Bookings", description = "Booking APIs for authenticated users")
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @Operation(summary = "Create booking", description = "Creates a new booking for the authenticated user")
    @PostMapping
    public BookingResponse createBooking(
            @Valid @RequestBody CreateBookingRequest request,
            Authentication authentication
    ) {
        return bookingService.createBooking(authentication.getName(), request);
    }

    @Operation(summary = "Get my bookings", description = "Returns bookings for the authenticated user")
    @GetMapping("/my")
    public List<BookingResponse> getMyBookings(Authentication authentication) {
        return bookingService.getMyBookings(authentication.getName());
    }

    @Operation(summary = "Cancel my booking", description = "Allows the authenticated user to cancel their own booking")
    @PatchMapping("/{bookingId}/cancel")
    public BookingResponse cancelMyBooking(
            @PathVariable Long bookingId,
            Authentication authentication
    ) {
        return bookingService.cancelMyBooking(authentication.getName(), bookingId);
    }

    @Operation(summary = "Get my booking by ID", description = "Returns a single booking owned by the authenticated user")
    @GetMapping("/{bookingId}")
    public BookingResponse getMyBookingById(
            @PathVariable Long bookingId,
            Authentication authentication
    ) {
        return bookingService.getMyBookingById(authentication.getName(), bookingId);
    }
}