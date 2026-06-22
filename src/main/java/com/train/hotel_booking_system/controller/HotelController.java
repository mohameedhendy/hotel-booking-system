package com.train.hotel_booking_system.controller;

import com.train.hotel_booking_system.dto.HotelResponse;
import com.train.hotel_booking_system.dto.RoomResponse;
import com.train.hotel_booking_system.service.HotelService;
import com.train.hotel_booking_system.service.RoomService;
import org.springframework.web.bind.annotation.*;
import com.train.hotel_booking_system.dto.PageResponse;
import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Public Hotels", description = "Public APIs for browsing and searching hotels")
@RestController
@RequestMapping("/api/hotels")
public class HotelController {

    private final HotelService hotelService;
    private final RoomService roomService;

    public HotelController(HotelService hotelService, RoomService roomService) {
        this.hotelService = hotelService;
        this.roomService = roomService;
    }

    @Operation(summary = "Get all hotels", description = "Returns all hotels without authentication")
    @GetMapping
    public List<HotelResponse> getAllHotels() {
        return hotelService.getAllHotels();
    }

    @Operation(summary = "Get hotel by ID", description = "Returns hotel details by hotel ID")
    @GetMapping("/{hotelId}")
    public HotelResponse getHotelById(@PathVariable Long hotelId) {
        return hotelService.getHotelById(hotelId);
    }

    @Operation(summary = "Get rooms by hotel", description = "Returns all rooms for a specific hotel")
    @GetMapping("/{hotelId}/rooms")
    public List<RoomResponse> getRoomsByHotelId(@PathVariable Long hotelId) {
        return roomService.getRoomsByHotelId(hotelId);
    }

    @Operation(summary = "Search hotels by city", description = "Searches hotels using city name")
    @GetMapping("/search")
    public List<HotelResponse> searchHotelsByCity(@RequestParam String city) {
        return hotelService.searchHotelsByCity(city);
    }

    @Operation(summary = "Get hotels with pagination", description = "Returns hotels using pagination and sorting")
    @GetMapping("/paged")
    public PageResponse<HotelResponse> getHotelsPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        return hotelService.getHotelsPaged(page, size, sortBy, direction);
    }
}