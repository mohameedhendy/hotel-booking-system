package com.train.hotel_booking_system.controller;

import com.train.hotel_booking_system.dto.RoomResponse;
import com.train.hotel_booking_system.entity.RoomType;
import com.train.hotel_booking_system.service.RoomService;
import org.springframework.web.bind.annotation.*;
import com.train.hotel_booking_system.dto.PageResponse;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;


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

    @Operation(
            summary = "Search available rooms by date range",
            description = """
                    Returns rooms that are available and not booked during the selected date range.
                    Optional filters can be used: city, hotelId, and roomType.
                    """
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Available rooms returned successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid date range"),
            @ApiResponse(responseCode = "500", description = "Unexpected server error")
    })

    @GetMapping("/search-available")
    public List<RoomResponse> searchAvailableRooms(
            @Parameter(
                    description = "Optional city filter",
                    example = "Riyadh"
            )
            @RequestParam(required = false) String city,

            @Parameter(
                    description = "Optional hotel ID filter",
                    example = "1"
            )
            @RequestParam(required = false) Long hotelId,

            @Parameter(
                    description = "Optional room type filter",
                    example = "DOUBLE"
            )
            @RequestParam(required = false) RoomType roomType,

            @Parameter(
                    description = "Check-in date",
                    example = "2026-07-10",
                    required = true
            )
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate checkInDate,

            @Parameter(
                    description = "Check-out date",
                    example = "2026-07-15",
                    required = true
            )
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate checkOutDate
    ) {
        return roomService.searchAvailableRooms(
                checkInDate,
                checkOutDate,
                city,
                hotelId,
                roomType
        );
    }
}