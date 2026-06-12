package com.train.hotel_booking_system.dto;

import com.train.hotel_booking_system.entity.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private Role role;

}
