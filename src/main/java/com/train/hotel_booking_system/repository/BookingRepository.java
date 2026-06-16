package com.train.hotel_booking_system.repository;

import com.train.hotel_booking_system.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}