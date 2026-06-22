package com.train.hotel_booking_system.controller;

import com.train.hotel_booking_system.dto.CreateRoomRequest;
import com.train.hotel_booking_system.dto.RoomResponse;
import com.train.hotel_booking_system.service.RoomService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import com.train.hotel_booking_system.dto.UpdateRoomRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import java.util.List;

@RestController
@RequestMapping("/api/admin/rooms")
public class AdminRoomController {

    private final RoomService roomService;

    public AdminRoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping("/hotel/{hotelId}")
    public RoomResponse createRoom(
            @PathVariable Long hotelId,
            @Valid @RequestBody CreateRoomRequest request
    ) {
        return roomService.createRoom(hotelId, request);
    }

    @GetMapping
    public List<RoomResponse> getAllRooms() {
        return roomService.getAllRooms();
    }

    @GetMapping("/{roomId}")
    public RoomResponse getRoomById(@PathVariable Long roomId) {
        return roomService.getRoomById(roomId);
    }

    @GetMapping("/hotel/{hotelId}")
    public List<RoomResponse> getRoomsByHotelId(@PathVariable Long hotelId) {
        return roomService.getRoomsByHotelId(hotelId);
    }

    @PutMapping("/{roomId}")
    public RoomResponse updateRoom(
            @PathVariable Long roomId,
            @Valid @RequestBody UpdateRoomRequest request
    ) {
        return roomService.updateRoom(roomId, request);
    }

    @DeleteMapping("/{roomId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRoom(@PathVariable Long roomId) {
        roomService.deleteRoom(roomId);
    }
}