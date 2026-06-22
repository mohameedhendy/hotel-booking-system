package com.train.hotel_booking_system.repository;

import com.train.hotel_booking_system.entity.Room;
import com.train.hotel_booking_system.entity.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {

    List<Room> findByHotelId(Long hotelId);

    List<Room> findByAvailableTrue();

    List<Room> findByRoomType(RoomType roomType);

    List<Room> findByHotelIdAndAvailableTrue(Long hotelId);

    boolean existsByHotelId(Long hotelId);
}