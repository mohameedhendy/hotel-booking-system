# Hotel Booking System API

A backend-only **Hotel Booking System** built with **Java Spring Boot**.

This project provides a complete REST API for hotel browsing, room management, booking management, authentication, authorization, admin dashboard statistics, Swagger documentation, PostgreSQL integration, and Docker support.

---

## Overview

The project is designed as a real-world backend portfolio project that demonstrates clean API design, layered architecture, authentication with JWT, role-based access control, validation, exception handling, database relationships, and Dockerized deployment.

The system supports two main roles:

* `USER`
* `ADMIN`

Users can browse hotels and rooms, search for available rooms, create bookings, view their own bookings, and cancel bookings.

Admins can manage hotels, rooms, bookings, and view dashboard statistics.

---

## Features

### Authentication & Authorization

* User registration
* User login
* JWT-based authentication
* Stateless security using Spring Security
* Role-based access control
* `USER` and `ADMIN` roles
* Current authenticated user endpoint
* Protected APIs using Bearer Token

---

### User Features

* Register a new account
* Login and receive JWT token
* View current user details
* Browse hotels
* Browse rooms
* Search available rooms by date range
* Filter available rooms by:

    * City
    * Hotel ID
    * Room type
* Create room bookings
* View personal bookings
* View a single personal booking by ID
* Cancel own bookings

---

### Admin Features

* Create hotels
* Update hotels
* Delete hotels
* View all hotels
* View hotel details
* Create rooms under specific hotels
* Update rooms
* Delete rooms
* View all rooms
* View rooms by hotel
* View all bookings
* View booking details by ID
* Update booking status
* Cancel bookings
* View admin dashboard statistics

---

## Admin Dashboard Statistics

The project includes an admin statistics endpoint that returns important system metrics:

* Total users
* Total hotels
* Total rooms
* Total available rooms
* Total bookings
* Pending bookings
* Confirmed bookings
* Cancelled bookings
* Completed bookings
* Confirmed booking revenue

Endpoint:

```http
GET /api/admin/statistics
```

---

## Booking Rules

The booking system includes business rules to prevent invalid booking flows.

### Booking Statuses

```text
PENDING
CONFIRMED
CANCELLED
COMPLETED
```

### Allowed Status Transitions

```text
PENDING   -> CONFIRMED
PENDING   -> CANCELLED
CONFIRMED -> COMPLETED
CONFIRMED -> CANCELLED
```

### Blocked Status Transitions

Once a booking is:

```text
CANCELLED
COMPLETED
```

Its status cannot be changed again.

---

## Room Availability Search

The system supports searching for available rooms by date range.

Endpoint:

```http
GET /api/rooms/search-available
```

Required parameters:

```text
checkInDate
checkOutDate
```

Optional filters:

```text
city
hotelId
roomType
```

Example:

```http
GET /api/rooms/search-available?checkInDate=2026-07-10&checkOutDate=2026-07-15&city=Riyadh&roomType=DOUBLE
```

The filtering is handled directly in the database query for better performance.

---

## Pagination & Sorting

The project supports pagination and sorting for hotels and rooms.

### Hotels Pagination

```http
GET /api/hotels/paged?page=0&size=5&sortBy=rating&direction=desc
```

Allowed hotel sorting fields:

```text
id
name
city
rating
```

### Rooms Pagination

```http
GET /api/rooms/paged?page=0&size=5&sortBy=pricePerNight&direction=asc
```

Allowed room sorting fields:

```text
id
roomNumber
roomType
pricePerNight
capacity
```

Invalid sorting fields return:

```http
400 Bad Request
```

---

## Validation

The project includes request validation for important input fields.

Examples:

* Email must be valid
* Password must have a minimum length
* Hotel name and city are required
* Hotel rating must be between `0` and `5`
* Room price must be greater than `0`
* Room capacity must be greater than `0`
* Check-in date cannot be in the past
* Check-out date must be after check-in date
* Invalid request body returns a clean error response
* Invalid enum values return a clean error response

---

## Error Handling

The project includes a global exception handler that returns consistent error responses.

Handled cases include:

* Validation errors
* Invalid request body
* Invalid enum values
* Not found errors
* Conflict errors
* Unauthorized login attempts
* Unexpected server errors

Example error response:

```json
{
  "timestamp": "2026-06-23T20:30:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Invalid request body",
  "path": "/api/bookings"
}
```

---

## HTTP Status Improvements

The project uses accurate HTTP status codes.

Examples:

### Resource Not Found

```http
404 Not Found
```

### Invalid Request

```http
400 Bad Request
```

### Duplicate or Conflicting Data

```http
409 Conflict
```

Examples:

* Trying to delete a hotel that still has rooms
* Trying to delete a room that still has bookings
* Trying to create a duplicate room number inside the same hotel

---

## Security Improvements

The project includes several security-related improvements:

* Passwords are hashed using BCrypt
* JWT secret is loaded from environment variables
* JWT signing key generation is centralized
* JWT secret length is validated
* Security context is not overwritten if authentication already exists
* Sensitive environment files are ignored by Git
* `.env.example` is provided as a safe template

---

## Tech Stack

* Java 21
* Spring Boot
* Spring Security
* JWT
* Spring Data JPA
* Hibernate
* PostgreSQL
* Maven
* Lombok
* Jakarta Validation
* Swagger / OpenAPI
* Docker
* Docker Compose

---

## Project Structure

```text
src/main/java/com/train/hotel_booking_system
├── config
├── controller
├── dto
├── entity
├── exception
├── repository
├── security
└── service
```

---

## Main Entities

### User

Represents application users.

Main fields:

```text
id
firstName
lastName
email
password
role
```

---

### Hotel

Represents hotels.

Main fields:

```text
id
name
city
address
description
rating
rooms
```

---

### Room

Represents hotel rooms.

Main fields:

```text
id
roomNumber
roomType
pricePerNight
capacity
available
hotel
bookings
```

---

### Booking

Represents room bookings.

Main fields:

```text
id
checkInDate
checkOutDate
totalPrice
status
user
room
```

---

## API Documentation

Swagger UI is available at:

```text
http://localhost:8080/swagger-ui.html
```

OpenAPI JSON is available at:

```text
http://localhost:8080/v3/api-docs
```

---

## Main API Endpoints

### Authentication

```http
POST /api/users/register
POST /api/auth/login
GET  /api/auth/me
```

---

### Public Hotels

```http
GET /api/hotels
GET /api/hotels/{hotelId}
GET /api/hotels/{hotelId}/rooms
GET /api/hotels/search?city=Riyadh
GET /api/hotels/paged?page=0&size=5&sortBy=rating&direction=desc
```

---

### Admin Hotels

```http
POST   /api/admin/hotels
GET    /api/admin/hotels
GET    /api/admin/hotels/{hotelId}
PUT    /api/admin/hotels/{hotelId}
DELETE /api/admin/hotels/{hotelId}
```

---

### Public Rooms

```http
GET /api/rooms/{roomId}
GET /api/rooms/available
GET /api/rooms/type/DOUBLE
GET /api/rooms/paged?page=0&size=5&sortBy=pricePerNight&direction=asc
GET /api/rooms/search-available?checkInDate=2026-07-10&checkOutDate=2026-07-15
```

Search with filters:

```http
GET /api/rooms/search-available?checkInDate=2026-07-10&checkOutDate=2026-07-15&city=Riyadh&roomType=DOUBLE
```

---

### Admin Rooms

```http
POST   /api/admin/rooms/hotel/{hotelId}
GET    /api/admin/rooms
GET    /api/admin/rooms/{roomId}
GET    /api/admin/rooms/hotel/{hotelId}
PUT    /api/admin/rooms/{roomId}
DELETE /api/admin/rooms/{roomId}
```

---

### User Bookings

```http
POST  /api/bookings
GET   /api/bookings/my
GET   /api/bookings/{bookingId}
PATCH /api/bookings/{bookingId}/cancel
```

---

### Admin Bookings

```http
GET   /api/admin/bookings
GET   /api/admin/bookings/{bookingId}
PATCH /api/admin/bookings/{bookingId}/status
PATCH /api/admin/bookings/{bookingId}/cancel
```

---

### Admin Statistics

```http
GET /api/admin/statistics
```

---

## Environment Variables

The application uses environment variables for sensitive data.

Required variables:

```env
DB_PASSWORD=your_database_password
JWT_SECRET=your_jwt_secret
APP_SEED_ENABLED=true
```

Important:

```text
.env should not be committed to GitHub.
.env.example is safe to commit.
```

---

## Demo Data Seeder

The project includes a demo data seeder.

When enabled, it creates:

* Demo admin account
* Demo user account
* Demo hotel
* Demo rooms

Enable it using:

```env
APP_SEED_ENABLED=true
```

---

## Demo Accounts

When demo data seeding is enabled, the following accounts are available.

### Admin Account

```json
{
  "email": "admin@example.com",
  "password": "admin123"
}
```

### User Account

```json
{
  "email": "user@example.com",
  "password": "user123"
}
```

---

## Run Locally Without Docker

### 1. Create PostgreSQL Database

Create a database named:

```text
hotel_booking_db
```

---

### 2. Set Environment Variables

Example:

```env
DB_PASSWORD=your_database_password
JWT_SECRET=your_jwt_secret
APP_SEED_ENABLED=true
```

---

### 3. Run the Application

On Linux/macOS:

```bash
./mvnw spring-boot:run
```

On Windows:

```powershell
mvnw spring-boot:run
```

Application URL:

```text
http://localhost:8080
```

Swagger URL:

```text
http://localhost:8080/swagger-ui.html
```

---

## Run With Docker

### 1. Create `.env`

Create a `.env` file in the project root.

Example:

```env
DB_PASSWORD=postgres
JWT_SECRET=ThisIsALongDemoJwtSecretForLocalDockerOnlyChangeMe123456789
```

---

### 2. Start the Application

```bash
docker compose up -d --build
```

---

### 3. Check Running Containers

```bash
docker compose ps
```

Expected containers:

```text
hotel_booking_app
hotel_booking_postgres
```

---

### 4. Open Swagger

```text
http://localhost:8080/swagger-ui.html
```

---

### 5. Stop Containers

```bash
docker compose down
```

---

### 6. Stop Containers and Remove Database Volume

```bash
docker compose down -v
```

Use this only when you want to delete Docker PostgreSQL data and start from scratch.

---

## Docker Notes

Docker Compose runs:

* Spring Boot application on port `8080`
* PostgreSQL container exposed on port `5433`

PostgreSQL runs inside Docker on:

```text
postgres:5432
```

From the host machine, PostgreSQL is available on:

```text
localhost:5433
```

---

## Example Login Request

```http
POST /api/auth/login
```

Request body:

```json
{
  "email": "admin@example.com",
  "password": "admin123"
}
```

Example response:

```json
{
  "token": "jwt_token_here"
}
```

Use the token as:

```text
Bearer jwt_token_here
```

---

## Current User Example

Endpoint:

```http
GET /api/auth/me
```

Expected response:

```json
{
  "id": 1,
  "firstName": "Demo",
  "lastName": "Admin",
  "email": "admin@example.com",
  "role": "ADMIN"
}
```

---

## Security Notes

* Passwords are not stored as plain text.
* Passwords are encrypted using BCrypt.
* JWT secret is not hardcoded.
* JWT secret is loaded from environment variables.
* JWT secret must be at least 32 bytes.
* `.env` is ignored by Git.
* `.env.example` contains only safe demo placeholders.
* Admin APIs require `ADMIN` role.
* User booking APIs require authentication.
* Public browsing APIs do not require authentication.

---

## What This Project Demonstrates

This project demonstrates practical backend development skills including:

* REST API design
* Layered architecture
* DTO usage
* Entity relationships
* Authentication and authorization
* JWT implementation
* Spring Security configuration
* Role-based access control
* Request validation
* Exception handling
* Pagination and sorting
* Database querying with JPQL
* Business rules implementation
* Dockerized application setup
* Swagger API documentation
* Environment-based configuration