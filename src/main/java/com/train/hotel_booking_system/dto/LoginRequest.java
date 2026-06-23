package com.train.hotel_booking_system.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Request body for user login")
public class LoginRequest {

    @Schema(description = "User email address", example = "user@example.com")
    @NotBlank
    @Email
    private String email;

    @Schema(description = "User password", example = "123456")
    @NotBlank
    @Size(min = 6, max = 100)
    private String password;
}