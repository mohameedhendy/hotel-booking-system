package com.train.hotel_booking_system.service;

import com.train.hotel_booking_system.dto.CreateRoomRequest;
import com.train.hotel_booking_system.dto.PageResponse;
import com.train.hotel_booking_system.dto.RoomResponse;
import com.train.hotel_booking_system.dto.UpdateRoomRequest;
import com.train.hotel_booking_system.entity.BookingStatus;
import com.train.hotel_booking_system.entity.Hotel;
import com.train.hotel_booking_system.entity.Room;
import com.train.hotel_booking_system.entity.RoomType;
import com.train.hotel_booking_system.repository.BookingRepository;
import com.train.hotel_booking_system.repository.HotelRepository;
import com.train.hotel_booking_system.repository.RoomRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
public class RoomService {

    private static final List<String> ALLOWED_SORT_FIELDS =
            List.of("id", "roomNumber", "roomType", "pricePerNight", "capacity");

    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;
    private final BookingRepository bookingRepository;

    public RoomService(
            RoomRepository roomRepository,
            HotelRepository hotelRepository,
            BookingRepository bookingRepository
    ) {
        this.roomRepository = roomRepository;
        this.hotelRepository = hotelRepository;
        this.bookingRepository = bookingRepository;
    }

    @Transactional
    public RoomResponse createRoom(Long hotelId, CreateRoomRequest request) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Hotel not found"));

        if (roomRepository.existsByHotelIdAndRoomNumber(hotelId, request.getRoomNumber())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Room number already exists in this hotel"
            );
        }

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
        String validSortBy = validateSortBy(sortBy);
        Sort.Direction sortDirection = validateSortDirection(direction);

        PageRequest pageRequest = PageRequest.of(
                page,
                size,
                Sort.by(sortDirection, validSortBy)
        );

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

    @Transactional
    public RoomResponse updateRoom(Long roomId, UpdateRoomRequest request) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Room not found"));

        if (request.getRoomNumber() != null && !request.getRoomNumber().isBlank()) {
            boolean roomNumberChanged = !request.getRoomNumber().equals(room.getRoomNumber());

            if (
                    roomNumberChanged &&
                            roomRepository.existsByHotelIdAndRoomNumber(
                                    room.getHotel().getId(),
                                    request.getRoomNumber()
                            )
            ) {
                throw new ResponseStatusException(
                        HttpStatus.CONFLICT,
                        "Room number already exists in this hotel"
                );
            }

            room.setRoomNumber(request.getRoomNumber());
        }

        if (request.getRoomType() != null) {
            room.setRoomType(request.getRoomType());
        }

        if (request.getPricePerNight() != null) {
            room.setPricePerNight(request.getPricePerNight());
        }

        if (request.getCapacity() != null) {
            room.setCapacity(request.getCapacity());
        }

        if (request.getAvailable() != null) {
            room.setAvailable(request.getAvailable());
        }

        Room savedRoom = roomRepository.save(room);

        return mapToResponse(savedRoom);
    }

    @Transactional
    public void deleteRoom(Long roomId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Room not found"));

        if (bookingRepository.existsByRoomId(roomId)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Cannot delete room because it has bookings"
            );
        }

        roomRepository.delete(room);
    }

    @Transactional(readOnly = true)
    public List<RoomResponse> searchAvailableRooms(
            LocalDate checkInDate,
            LocalDate checkOutDate,
            String city,
            Long hotelId,
            RoomType roomType
    ) {
        if (!checkOutDate.isAfter(checkInDate)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Check-out date must be after check-in date"
            );
        }

        return roomRepository.findAvailableRoomsByDateRangeAndFilters(
                        checkInDate,
                        checkOutDate,
                        BookingStatus.CANCELLED,
                        city,
                        hotelId,
                        roomType
                )
                .stream()
                .map(this::mapToResponse)
                .toList();
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

    private String validateSortBy(String sortBy) {
        if (sortBy == null || sortBy.isBlank()) {
            return "id";
        }

        if (!ALLOWED_SORT_FIELDS.contains(sortBy)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Invalid sort field. Allowed fields: " + ALLOWED_SORT_FIELDS
            );
        }

        return sortBy;
    }

    private Sort.Direction validateSortDirection(String direction) {
        if (direction == null || direction.isBlank()) {
            return Sort.Direction.ASC;
        }

        if (direction.equalsIgnoreCase("asc")) {
            return Sort.Direction.ASC;
        }

        if (direction.equalsIgnoreCase("desc")) {
            return Sort.Direction.DESC;
        }

        throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Invalid sort direction. Use asc or desc"
        );
    }
}