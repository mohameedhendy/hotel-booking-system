package com.train.hotel_booking_system.service;

import com.train.hotel_booking_system.dto.CreateRoomRequest;
import com.train.hotel_booking_system.dto.RoomResponse;
import com.train.hotel_booking_system.entity.Hotel;
import com.train.hotel_booking_system.entity.Room;
import com.train.hotel_booking_system.entity.RoomType;
import com.train.hotel_booking_system.repository.HotelRepository;
import com.train.hotel_booking_system.repository.RoomRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import com.train.hotel_booking_system.dto.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

@Service
public class RoomService {

    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;

    public RoomService(RoomRepository roomRepository, HotelRepository hotelRepository) {
        this.roomRepository = roomRepository;
        this.hotelRepository = hotelRepository;
    }

    @Transactional
    public RoomResponse createRoom(Long hotelId, CreateRoomRequest request) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Hotel not found"));

        Room room = new Room();
        room.setRoomNumber(request.getRoomNumber());
        room.setRoomType(request.getRoomType());
        room.setPricePerNight(request.getPricePerNight());
        room.setCapacity(request.getCapacity());
        room.setAvailable(request.getAvailable() != null ? request.getAvailable() : true);
        room.setHotel(hotel);

        Room savedRoom = roomRepository.save(room);

        return mapToResponse(savedRoom);
    }

    @Transactional(readOnly = true)
    public List<RoomResponse> getAllRooms() {
        return roomRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<RoomResponse> getRoomsByHotelId(Long hotelId) {
        return roomRepository.findByHotelId(hotelId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public RoomResponse getRoomById(Long roomId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Room not found"));

        return mapToResponse(room);
    }

    private RoomResponse mapToResponse(Room room) {
        RoomResponse response = new RoomResponse();

        response.setId(room.getId());
        response.setRoomNumber(room.getRoomNumber());
        response.setRoomType(room.getRoomType());
        response.setPricePerNight(room.getPricePerNight());
        response.setCapacity(room.getCapacity());
        response.setAvailable(room.getAvailable());

        response.setHotelId(room.getHotel().getId());
        response.setHotelName(room.getHotel().getName());

        return response;
    }

    @Transactional(readOnly = true)
    public List<RoomResponse> getAvailableRooms() {
        return roomRepository.findByAvailableTrue()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<RoomResponse> getRoomsByType(RoomType roomType) {
        return roomRepository.findByRoomType(roomType)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public PageResponse<RoomResponse> getRoomsPaged(
            int page,
            int size,
            String sortBy,
            String direction
    ) {
        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        PageRequest pageRequest = PageRequest.of(page, size, sort);

        Page<RoomResponse> roomsPage = roomRepository.findAll(pageRequest)
                .map(this::mapToResponse);

        return new PageResponse<>(
                roomsPage.getContent(),
                roomsPage.getNumber(),
                roomsPage.getSize(),
                roomsPage.getTotalElements(),
                roomsPage.getTotalPages(),
                roomsPage.isLast()
        );
    }
}