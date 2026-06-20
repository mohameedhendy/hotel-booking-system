package com.train.hotel_booking_system.controller;

import com.train.hotel_booking_system.dto.BookingResponse;
import com.train.hotel_booking_system.service.BookingService;
import org.springframework.web.bind.annotation.*;
import com.train.hotel_booking_system.dto.UpdateBookingStatusRequest;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/admin/bookings")
public class AdminBookingController {

    private final BookingService bookingService;

    public AdminBookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping
    public List<BookingResponse> getAllBookings() {
        return bookingService.getAllBookings();
    }

    @PatchMapping("/{bookingId}/status")
    public BookingResponse updateBookingStatus(
            @PathVariable Long bookingId,
            @Valid @RequestBody UpdateBookingStatusRequest request
    ) {
        return bookingService.updateBookingStatus(bookingId, request.getStatus());
    }
}