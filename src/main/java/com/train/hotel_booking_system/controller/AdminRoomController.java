package com.train.hotel_booking_system.controller;

import com.train.hotel_booking_system.dto.CreateRoomRequest;
import com.train.hotel_booking_system.dto.RoomResponse;
import com.train.hotel_booking_system.dto.UpdateRoomRequest;
import com.train.hotel_booking_system.service.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "Admin Rooms", description = "Admin APIs for managing rooms")
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/admin/rooms")
public class AdminRoomController {

    private final RoomService roomService;

    public AdminRoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @Operation(summary = "Create room", description = "Creates a new room for a specific hotel. Admin only.")
    @PostMapping("/hotel/{hotelId}")
    public RoomResponse createRoom(
            @PathVariable Long hotelId,
            @Valid @RequestBody CreateRoomRequest request
    ) {
        return roomService.createRoom(hotelId, request);
    }

    @Operation(summary = "Get all rooms", description = "Returns all rooms. Admin only.")
    @GetMapping
    public List<RoomResponse> getAllRooms() {
        return roomService.getAllRooms();
    }

    @Operation(summary = "Get room by ID", description = "Returns room details by ID. Admin only.")
    @GetMapping("/{roomId}")
    public RoomResponse getRoomById(@PathVariable Long roomId) {
        return roomService.getRoomById(roomId);
    }

    @Operation(summary = "Get rooms by hotel", description = "Returns rooms for a specific hotel. Admin only.")
    @GetMapping("/hotel/{hotelId}")
    public List<RoomResponse> getRoomsByHotelId(@PathVariable Long hotelId) {
        return roomService.getRoomsByHotelId(hotelId);
    }

    @Operation(summary = "Update room", description = "Updates room details. Admin only.")
    @PutMapping("/{roomId}")
    public RoomResponse updateRoom(
            @PathVariable Long roomId,
            @Valid @RequestBody UpdateRoomRequest request
    ) {
        return roomService.updateRoom(roomId, request);
    }

    @Operation(summary = "Delete room", description = "Deletes a room if it has no bookings. Admin only.")
    @DeleteMapping("/{roomId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRoom(@PathVariable Long roomId) {
        roomService.deleteRoom(roomId);
    }
}