# Hotel Booking System

A backend REST API for hotel booking management built with Spring Boot, PostgreSQL, and JWT Authentication.

## Features

* User Registration
* User Login
* Password Encryption using BCrypt
* JWT Authentication & Authorization
* PostgreSQL Database Integration
* Spring Security Configuration
* RESTful APIs
* Role-Based Access Control (In Progress)

## Tech Stack

* Java
* Spring Boot
* Spring Security
* Spring Data JPA
* Hibernate
* PostgreSQL
* JWT
* Maven
* Git & GitHub

## API Endpoints

### Register User

POST /api/users/register

### Login

POST /api/auth/login

### Get Current User

GET /api/auth/me

Authorization Header:

Authorization: Bearer <JWT_TOKEN>

## Project Structure

src/main/java

* controller
* service
* repository
* entity
* dto
* security
* config
* exception

## Current Progress

* User Registration
* Login Authentication
* Password Encryption
* JWT Token Generation
* JWT Authentication Filter
* PostgreSQL Integration

## Upcoming Features

* Role-Based Authorization
* Hotel Management
* Room Management
* Booking Management
* Reservation Workflow
* Admin Dashboard APIs


## Database ERD

erDiagram
    USERS ||--o{ BOOKINGS : makes
    HOTELS ||--o{ ROOMS : contains
    ROOMS ||--o{ BOOKINGS : reserved_for

    USERS {
        Long id PK
        String first_name
        String last_name
        String email
        String password
        Role role
    }

    HOTELS {
        Long id PK
        String name
        String city
        String address
        String description
        Double rating
    }

    ROOMS {
        Long id PK
        String room_number
        RoomType room_type
        BigDecimal price_per_night
        Integer capacity
        Boolean available
        Long hotel_id FK
    }

    BOOKINGS {
        Long id PK
        LocalDate check_in_date
        LocalDate check_out_date
        BigDecimal total_price
        BookingStatus status
        Long user_id FK
        Long room_id FK
    }

## Author

Mohamed Hendy

Backend Developer | Java & Spring Boot
