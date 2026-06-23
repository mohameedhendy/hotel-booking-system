package com.train.hotel_booking_system.controller;

import com.train.hotel_booking_system.dto.CreateHotelRequest;
import com.train.hotel_booking_system.dto.HotelResponse;
import com.train.hotel_booking_system.service.HotelService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import com.train.hotel_booking_system.dto.UpdateHotelRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;


@Tag(name = "Admin Hotels", description = "Admin APIs for managing hotels")
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/admin/hotels")
public class AdminHotelController {

    private final HotelService hotelService;

    public AdminHotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @Operation(summary = "Create hotel", description = "Creates a new hotel. Admin only.")
    @PostMapping
    public HotelResponse createHotel(@Valid @RequestBody CreateHotelRequest request) {
        return hotelService.createHotel(request);
    }

    @Operation(summary = "Get all hotels", description = "Returns all hotels. Admin only.")
    @GetMapping
    public List<HotelResponse> getAllHotels() {
        return hotelService.getAllHotels();
    }

    @Operation(summary = "Get hotel by ID", description = "Returns hotel details by ID. Admin only.")
    @GetMapping("/{id}")
    public HotelResponse getHotelById(@PathVariable Long id) {
        return hotelService.getHotelById(id);
    }

    @Operation(summary = "Update hotel", description = "Updates hotel details. Admin only.")
    @PutMapping("/{hotelId}")
    public HotelResponse updateHotel(
            @PathVariable Long hotelId,
            @Valid @RequestBody UpdateHotelRequest request
    ) {
        return hotelService.updateHotel(hotelId, request);
    }

    @Operation(summary = "Delete hotel", description = "Deletes a hotel if it has no rooms. Admin only.")
    @DeleteMapping("/{hotelId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteHotel(@PathVariable Long hotelId) {
        hotelService.deleteHotel(hotelId);
    }
}