package com.train.hotel_booking_system.config;

import com.train.hotel_booking_system.entity.Hotel;
import com.train.hotel_booking_system.entity.Role;
import com.train.hotel_booking_system.entity.Room;
import com.train.hotel_booking_system.entity.RoomType;
import com.train.hotel_booking_system.entity.User;
import com.train.hotel_booking_system.repository.HotelRepository;
import com.train.hotel_booking_system.repository.RoomRepository;
import com.train.hotel_booking_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;

@Configuration
public class DemoDataSeeder {

    @Value("${app.seed.enabled:false}")
    private boolean seedEnabled;

    @Bean
    public CommandLineRunner seedDemoData(
            UserRepository userRepository,
            HotelRepository hotelRepository,
            RoomRepository roomRepository,
            PasswordEncoder passwordEncoder
    ) {
        return args -> {
            if (!seedEnabled) {
                return;
            }

            createDemoUsers(userRepository, passwordEncoder);

            Hotel demoHotel = hotelRepository.findByNameIgnoreCase("Demo Hotel Riyadh")
                    .orElseGet(() -> {
                        Hotel hotel = new Hotel();
                        hotel.setName("Demo Hotel Riyadh");
                        hotel.setCity("Riyadh");
                        hotel.setAddress("King Fahd Road");
                        hotel.setDescription("Demo hotel for local testing");
                        hotel.setRating(4.8);

                        return hotelRepository.save(hotel);
                    });

            createRoomIfNotExists(
                    roomRepository,
                    demoHotel,
                    "101",
                    RoomType.DOUBLE,
                    new BigDecimal("250.00"),
                    2
            );

            createRoomIfNotExists(
                    roomRepository,
                    demoHotel,
                    "102",
                    RoomType.SINGLE,
                    new BigDecimal("150.00"),
                    1
            );

            createRoomIfNotExists(
                    roomRepository,
                    demoHotel,
                    "201",
                    RoomType.SUITE,
                    new BigDecimal("500.00"),
                    4
            );
        };
    }

    private void createDemoUsers(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        if (!userRepository.existsByEmail("admin@example.com")) {
            User admin = new User();
            admin.setFirstName("Demo");
            admin.setLastName("Admin");
            admin.setEmail("admin@example.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole(Role.ADMIN);

            userRepository.save(admin);
        }

        if (!userRepository.existsByEmail("user@example.com")) {
            User user = new User();
            user.setFirstName("Demo");
            user.setLastName("User");
            user.setEmail("user@example.com");
            user.setPassword(passwordEncoder.encode("user123"));
            user.setRole(Role.USER);

            userRepository.save(user);
        }
    }

    private void createRoomIfNotExists(
            RoomRepository roomRepository,
            Hotel hotel,
            String roomNumber,
            RoomType roomType,
            BigDecimal pricePerNight,
            Integer capacity
    ) {
        boolean roomExists = roomRepository.existsByHotelIdAndRoomNumber(
                hotel.getId(),
                roomNumber
        );

        if (roomExists) {
            return;
        }

        Room room = new Room();
        room.setRoomNumber(roomNumber);
        room.setRoomType(roomType);
        room.setPricePerNight(pricePerNight);
        room.setCapacity(capacity);
        room.setAvailable(true);
        room.setHotel(hotel);

        roomRepository.save(room);
    }
}