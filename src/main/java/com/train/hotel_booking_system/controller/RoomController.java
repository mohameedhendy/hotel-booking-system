package com.train.hotel_booking_system.controller;

import com.train.hotel_booking_system.dto.RoomResponse;
import com.train.hotel_booking_system.entity.RoomType;
import com.train.hotel_booking_system.service.RoomService;
import org.springframework.web.bind.annotation.*;
import com.train.hotel_booking_system.dto.PageResponse;
import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


@Tag(name = "Public Rooms", description = "Public APIs for browsing and filtering rooms")
@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @Operation(summary = "Get room by ID", description = "Returns room details by room ID")
    @GetMapping("/{roomId}")
    public RoomResponse getRoomById(@PathVariable Long roomId) {
        return roomService.getRoomById(roomId);
    }

    @Operation(summary = "Get available rooms", description = "Returns all currently available rooms")
    @GetMapping("/available")
    public List<RoomResponse> getAvailableRooms() {
        return roomService.getAvailableRooms();
    }

    @Operation(summary = "Filter rooms by type", description = "Returns rooms by room type such as SINGLE, DOUBLE, SUITE, or DELUXE")
    @GetMapping("/type/{roomType}")
    public List<RoomResponse> getRoomsByType(@PathVariable RoomType roomType) {
        return roomService.getRoomsByType(roomType);
    }

    @Operation(summary = "Get rooms with pagination", description = "Returns rooms using pagination and sorting")
    @GetMapping("/paged")
    public PageResponse<RoomResponse> getRoomsPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        return roomService.getRoomsPaged(page, size, sortBy, direction);
    }
}