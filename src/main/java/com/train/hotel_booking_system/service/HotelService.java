package com.train.hotel_booking_system.service;

import com.train.hotel_booking_system.dto.CreateHotelRequest;
import com.train.hotel_booking_system.dto.HotelResponse;
import com.train.hotel_booking_system.entity.Hotel;
import com.train.hotel_booking_system.repository.HotelRepository;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import com.train.hotel_booking_system.dto.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

@Service
public class HotelService {

    private final HotelRepository hotelRepository;

    public HotelService(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    public HotelResponse createHotel(CreateHotelRequest request) {
        Hotel hotel = new Hotel();
        hotel.setName(request.getName());
        hotel.setCity(request.getCity());
        hotel.setAddress(request.getAddress());
        hotel.setDescription(request.getDescription());
        hotel.setRating(request.getRating());

        Hotel savedHotel = hotelRepository.save(hotel);

        return mapToResponse(savedHotel);
    }

    public List<HotelResponse> getAllHotels() {
        return hotelRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public HotelResponse getHotelById(Long id) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Hotel not found"));

        return mapToResponse(hotel);
    }

    private HotelResponse mapToResponse(Hotel hotel) {
        HotelResponse response = new HotelResponse();
        response.setId(hotel.getId());
        response.setName(hotel.getName());
        response.setCity(hotel.getCity());
        response.setAddress(hotel.getAddress());
        response.setDescription(hotel.getDescription());
        response.setRating(hotel.getRating());

        return response;
    }

    @Transactional(readOnly = true)
    public List<HotelResponse> searchHotelsByCity(String city) {
        return hotelRepository.findByCityIgnoreCase(city)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public PageResponse<HotelResponse> getHotelsPaged(
            int page,
            int size,
            String sortBy,
            String direction
    ) {
        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        PageRequest pageRequest = PageRequest.of(page, size, sort);

        Page<HotelResponse> hotelsPage = hotelRepository.findAll(pageRequest)
                .map(this::mapToResponse);

        return new PageResponse<>(
                hotelsPage.getContent(),
                hotelsPage.getNumber(),
                hotelsPage.getSize(),
                hotelsPage.getTotalElements(),
                hotelsPage.getTotalPages(),
                hotelsPage.isLast()
        );
    }
}