package com.train.hotel_booking_system.service;

import com.train.hotel_booking_system.dto.BookingResponse;
import com.train.hotel_booking_system.dto.CreateBookingRequest;
import com.train.hotel_booking_system.entity.Booking;
import com.train.hotel_booking_system.entity.BookingStatus;
import com.train.hotel_booking_system.entity.Room;
import com.train.hotel_booking_system.entity.User;
import com.train.hotel_booking_system.repository.BookingRepository;
import com.train.hotel_booking_system.repository.RoomRepository;
import com.train.hotel_booking_system.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    public BookingService(
            BookingRepository bookingRepository,
            RoomRepository roomRepository,
            UserRepository userRepository
    ) {
        this.bookingRepository = bookingRepository;
        this.roomRepository = roomRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public BookingResponse createBooking(String userEmail, CreateBookingRequest request) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Room not found"));

        if (!request.getCheckOutDate().isAfter(request.getCheckInDate())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Check-out date must be after check-in date"
            );
        }

        if (Boolean.FALSE.equals(room.getAvailable())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Room is not available"
            );
        }

        List<Booking> overlappingBookings = bookingRepository.findOverlappingBookings(
                room.getId(),
                request.getCheckInDate(),
                request.getCheckOutDate(),
                BookingStatus.CANCELLED
        );

        if (!overlappingBookings.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Room is already booked for selected dates"
            );
        }

        long nights = ChronoUnit.DAYS.between(
                request.getCheckInDate(),
                request.getCheckOutDate()
        );

        BigDecimal totalPrice = room.getPricePerNight()
                .multiply(BigDecimal.valueOf(nights));

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setRoom(room);
        booking.setCheckInDate(request.getCheckInDate());
        booking.setCheckOutDate(request.getCheckOutDate());
        booking.setTotalPrice(totalPrice);
        booking.setStatus(BookingStatus.PENDING);

        Booking savedBooking = bookingRepository.save(booking);

        return mapToResponse(savedBooking);
    }

    @Transactional(readOnly = true)
    public List<BookingResponse> getMyBookings(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        return bookingRepository.findByUserId(user.getId())
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<BookingResponse> getAllBookings() {
        return bookingRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private BookingResponse mapToResponse(Booking booking) {
        BookingResponse response = new BookingResponse();

        response.setId(booking.getId());

        response.setUserId(booking.getUser().getId());
        response.setUserEmail(booking.getUser().getEmail());

        response.setRoomId(booking.getRoom().getId());
        response.setRoomNumber(booking.getRoom().getRoomNumber());

        response.setHotelId(booking.getRoom().getHotel().getId());
        response.setHotelName(booking.getRoom().getHotel().getName());

        response.setCheckInDate(booking.getCheckInDate());
        response.setCheckOutDate(booking.getCheckOutDate());

        long nights = ChronoUnit.DAYS.between(
                booking.getCheckInDate(),
                booking.getCheckOutDate()
        );

        response.setNights(nights);
        response.setTotalPrice(booking.getTotalPrice());
        response.setStatus(booking.getStatus());

        return response;
    }
}