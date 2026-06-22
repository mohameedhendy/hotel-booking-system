package com.train.hotel_booking_system.repository;

import com.train.hotel_booking_system.entity.Room;
import com.train.hotel_booking_system.entity.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import com.train.hotel_booking_system.entity.BookingStatus;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;
import com.train.hotel_booking_system.entity.BookingStatus;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;


public interface RoomRepository extends JpaRepository<Room, Long> {

    List<Room> findByHotelId(Long hotelId);

    List<Room> findByAvailableTrue();

    List<Room> findByRoomType(RoomType roomType);

    List<Room> findByHotelIdAndAvailableTrue(Long hotelId);

    boolean existsByHotelId(Long hotelId);

    @Query("""
        SELECT r FROM Room r
        JOIN FETCH r.hotel
        WHERE r.available = true
        AND NOT EXISTS (
            SELECT b FROM Booking b
            WHERE b.room = r
            AND b.status <> :cancelledStatus
            AND b.checkInDate < :checkOutDate
            AND b.checkOutDate > :checkInDate
        )
        """)
    List<Room> findAvailableRoomsByDateRange(
            @Param("checkInDate") LocalDate checkInDate,
            @Param("checkOutDate") LocalDate checkOutDate,
            @Param("cancelledStatus") BookingStatus cancelledStatus
    );
}