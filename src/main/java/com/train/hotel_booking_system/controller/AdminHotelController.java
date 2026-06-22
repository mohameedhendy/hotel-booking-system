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

@RestController
@RequestMapping("/api/admin/hotels")
public class AdminHotelController {

    private final HotelService hotelService;

    public AdminHotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @PostMapping
    public HotelResponse createHotel(@Valid @RequestBody CreateHotelRequest request) {
        return hotelService.createHotel(request);
    }

    @GetMapping
    public List<HotelResponse> getAllHotels() {
        return hotelService.getAllHotels();
    }

    @GetMapping("/{id}")
    public HotelResponse getHotelById(@PathVariable Long id) {
        return hotelService.getHotelById(id);
    }

    @PutMapping("/{hotelId}")
    public HotelResponse updateHotel(
            @PathVariable Long hotelId,
            @RequestBody UpdateHotelRequest request
    ) {
        return hotelService.updateHotel(hotelId, request);
    }

    @DeleteMapping("/{hotelId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteHotel(@PathVariable Long hotelId) {
        hotelService.deleteHotel(hotelId);
    }
}