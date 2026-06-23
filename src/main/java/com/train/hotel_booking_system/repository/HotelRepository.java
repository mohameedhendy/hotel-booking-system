package com.train.hotel_booking_system.repository;

import com.train.hotel_booking_system.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HotelRepository extends JpaRepository<Hotel, Long> {

    List<Hotel> findByCityIgnoreCase(String city);

    Optional<Hotel> findByNameIgnoreCase(String name);

}