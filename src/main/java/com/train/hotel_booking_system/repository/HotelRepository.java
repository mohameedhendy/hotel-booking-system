package com.train.hotel_booking_system.repository;

import com.train.hotel_booking_system.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
}