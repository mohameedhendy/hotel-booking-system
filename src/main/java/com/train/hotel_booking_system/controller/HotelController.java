package com.train.hotel_booking_system.controller;

import com.train.hotel_booking_system.dto.HotelResponse;
import com.train.hotel_booking_system.dto.RoomResponse;
import com.train.hotel_booking_system.service.HotelService;
import com.train.hotel_booking_system.service.RoomService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hotels")
public class HotelController {

    private final HotelService hotelService;
    private final RoomService roomService;

    public HotelController(HotelService hotelService, RoomService roomService) {
        this.hotelService = hotelService;
        this.roomService = roomService;
    }

    @GetMapping
    public List<HotelResponse> getAllHotels() {
        return hotelService.getAllHotels();
    }

    @GetMapping("/{hotelId}")
    public HotelResponse getHotelById(@PathVariable Long hotelId) {
        return hotelService.getHotelById(hotelId);
    }

    @GetMapping("/{hotelId}/rooms")
    public List<RoomResponse> getRoomsByHotelId(@PathVariable Long hotelId) {
        return roomService.getRoomsByHotelId(hotelId);
    }
}