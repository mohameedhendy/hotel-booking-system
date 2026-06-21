package com.train.hotel_booking_system.controller;

import com.train.hotel_booking_system.dto.RoomResponse;
import com.train.hotel_booking_system.entity.RoomType;
import com.train.hotel_booking_system.service.RoomService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping("/{roomId}")
    public RoomResponse getRoomById(@PathVariable Long roomId) {
        return roomService.getRoomById(roomId);
    }

    @GetMapping("/available")
    public List<RoomResponse> getAvailableRooms() {
        return roomService.getAvailableRooms();
    }

    @GetMapping("/type/{roomType}")
    public List<RoomResponse> getRoomsByType(
            @PathVariable RoomType roomType
    ) {
        return roomService.getRoomsByType(roomType);
    }
}