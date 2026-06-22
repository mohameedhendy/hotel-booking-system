package com.train.hotel_booking_system.repository;

import com.train.hotel_booking_system.entity.Booking;
import com.train.hotel_booking_system.entity.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByUserId(Long userId);

    @Query("""
            SELECT b FROM Booking b
            WHERE b.room.id = :roomId
            AND b.status <> :cancelledStatus
            AND b.checkInDate < :checkOutDate
            AND b.checkOutDate > :checkInDate
            """)
    List<Booking> findOverlappingBookings(
            @Param("roomId") Long roomId,
            @Param("checkInDate") LocalDate checkInDate,
            @Param("checkOutDate") LocalDate checkOutDate,
            @Param("cancelledStatus") BookingStatus cancelledStatus
    );

    boolean existsByRoomId(Long roomId);
}