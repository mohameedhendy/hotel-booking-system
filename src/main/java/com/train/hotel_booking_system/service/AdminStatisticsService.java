package com.train.hotel_booking_system.service;

import com.train.hotel_booking_system.dto.AdminStatisticsResponse;
import com.train.hotel_booking_system.entity.BookingStatus;
import com.train.hotel_booking_system.repository.BookingRepository;
import com.train.hotel_booking_system.repository.HotelRepository;
import com.train.hotel_booking_system.repository.RoomRepository;
import com.train.hotel_booking_system.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class AdminStatisticsService {

    private final UserRepository userRepository;
    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;
    private final BookingRepository bookingRepository;

    public AdminStatisticsService(
            UserRepository userRepository,
            HotelRepository hotelRepository,
            RoomRepository roomRepository,
            BookingRepository bookingRepository
    ) {
        this.userRepository = userRepository;
        this.hotelRepository = hotelRepository;
        this.roomRepository = roomRepository;
        this.bookingRepository = bookingRepository;
    }

    @Transactional(readOnly = true)
    public AdminStatisticsResponse getStatistics() {
        AdminStatisticsResponse response = new AdminStatisticsResponse();

        response.setTotalUsers(userRepository.count());
        response.setTotalHotels(hotelRepository.count());
        response.setTotalRooms(roomRepository.count());
        response.setAvailableRooms(roomRepository.countByAvailableTrue());

        response.setTotalBookings(bookingRepository.count());
        response.setPendingBookings(bookingRepository.countByStatus(BookingStatus.PENDING));
        response.setConfirmedBookings(bookingRepository.countByStatus(BookingStatus.CONFIRMED));
        response.setCancelledBookings(bookingRepository.countByStatus(BookingStatus.CANCELLED));
        response.setCompletedBookings(bookingRepository.countByStatus(BookingStatus.COMPLETED));

        BigDecimal confirmedRevenue =
                bookingRepository.sumTotalPriceByStatus(BookingStatus.CONFIRMED);

        response.setConfirmedRevenue(
                confirmedRevenue != null ? confirmedRevenue : BigDecimal.ZERO
        );

        return response;
    }
}