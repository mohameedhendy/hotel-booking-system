package com.train.hotel_booking_system.repository;

import com.train.hotel_booking_system.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
}