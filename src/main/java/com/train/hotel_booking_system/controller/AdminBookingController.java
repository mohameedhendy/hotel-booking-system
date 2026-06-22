package com.train.hotel_booking_system.controller;

import com.train.hotel_booking_system.dto.BookingResponse;
import com.train.hotel_booking_system.service.BookingService;
import org.springframework.web.bind.annotation.*;
import com.train.hotel_booking_system.dto.UpdateBookingStatusRequest;
import jakarta.validation.Valid;
import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;


@Tag(name = "Admin Bookings", description = "Admin APIs for managing bookings")
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/admin/bookings")
public class AdminBookingController {

    private final BookingService bookingService;

    public AdminBookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @Operation(summary = "Get all bookings", description = "Returns all bookings in the system. Admin only.")
    @GetMapping
    public List<BookingResponse> getAllBookings() {
        return bookingService.getAllBookings();
    }

    @Operation(summary = "Update booking status", description = "Updates booking status to PENDING, CONFIRMED, CANCELLED, or COMPLETED. Admin only.")
    @PatchMapping("/{bookingId}/status")
    public BookingResponse updateBookingStatus(
            @PathVariable Long bookingId,
            @Valid @RequestBody UpdateBookingStatusRequest request
    ) {
        return bookingService.updateBookingStatus(bookingId, request.getStatus());
    }
}