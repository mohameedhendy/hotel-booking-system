# Hotel Booking System API

A backend-only hotel booking system built with **Java Spring Boot**.
The project provides REST APIs for user authentication, hotel management, room management, bookings, admin dashboard
statistics, Swagger documentation, and Docker support.

---

## Features

* User registration and login
* JWT-based authentication
* Role-based authorization using `USER` and `ADMIN`
* Admin hotel management
* Admin room management
* Public hotel and room browsing
* Room availability search by date range
* User booking creation and cancellation
* Admin booking management
* Booking status transition rules
* Admin statistics dashboard
* Global exception handling
* Request validation
* Swagger/OpenAPI documentation
* Demo data seeding
* Docker and Docker Compose support

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

Represents system users.

Fields include:

* id
* firstName
* lastName
* email
* password
* role

### Hotel

Represents hotels.

Fields include:

* id
* name
* city
* address
* description
* rating

### Room

Represents hotel rooms.

Fields include:

* id
* roomNumber
* roomType
* pricePerNight
* capacity
* available
* hotel

### Booking

Represents room bookings.

Fields include:

* id
* checkInDate
* checkOutDate
* totalPrice
* status
* user
* room

---

## Roles

```text
USER
ADMIN
```

### USER can:

* Register
* Login
* View hotels
* View rooms
* Search available rooms
* Create bookings
* View own bookings
* Cancel own bookings

### ADMIN can:

* Manage hotels
* Manage rooms
* View all bookings
* Update booking status
* Cancel bookings
* View dashboard statistics

---

## Booking Statuses

```text
PENDING
CONFIRMED
CANCELLED
COMPLETED
```

Allowed status transitions:

```text
PENDING → CONFIRMED
PENDING → CANCELLED
CONFIRMED → COMPLETED
CONFIRMED → CANCELLED
```

The following statuses cannot be changed:

```text
CANCELLED
COMPLETED
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

### Public Hotels

```http
GET /api/hotels
GET /api/hotels/{hotelId}
GET /api/hotels/{hotelId}/rooms
GET /api/hotels/search?city=Riyadh
GET /api/hotels/paged?page=0&size=5&sortBy=rating&direction=desc
```

### Admin Hotels

```http
POST   /api/admin/hotels
GET    /api/admin/hotels
GET    /api/admin/hotels/{hotelId}
PUT    /api/admin/hotels/{hotelId}
DELETE /api/admin/hotels/{hotelId}
```

### Public Rooms

```http
GET /api/rooms/{roomId}
GET /api/rooms/available
GET /api/rooms/type/DOUBLE
GET /api/rooms/paged?page=0&size=5&sortBy=pricePerNight&direction=asc
GET /api/rooms/search-available?checkInDate=2026-07-10&checkOutDate=2026-07-15
```

Optional filters for available room search:

```text
city
hotelId
roomType
```

Example:

```http
GET /api/rooms/search-available?checkInDate=2026-07-10&checkOutDate=2026-07-15&city=Riyadh&roomType=DOUBLE
```

### Admin Rooms

```http
POST   /api/admin/rooms/hotel/{hotelId}
GET    /api/admin/rooms
GET    /api/admin/rooms/{roomId}
GET    /api/admin/rooms/hotel/{hotelId}
PUT    /api/admin/rooms/{roomId}
DELETE /api/admin/rooms/{roomId}
```

### User Bookings

```http
POST  /api/bookings
GET   /api/bookings/my
GET   /api/bookings/{bookingId}
PATCH /api/bookings/{bookingId}/cancel
```

### Admin Bookings

```http
GET   /api/admin/bookings
GET   /api/admin/bookings/{bookingId}
PATCH /api/admin/bookings/{bookingId}/status
PATCH /api/admin/bookings/{bookingId}/cancel
```

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

Do not commit `.env` to GitHub.

Use `.env.example` as a safe template.

---

## Run Locally Without Docker

### 1. Create PostgreSQL Database

Create a database named:

```text
hotel_booking_db
```

### 2. Set Environment Variables

Example:

```env
DB_PASSWORD=your_database_password
JWT_SECRET=your_jwt_secret
APP_SEED_ENABLED=true
```

### 3. Run the Application

```bash
./mvnw spring-boot:run
```

On Windows:

```powershell
mvnw spring-boot:run
```

The application will run on:

```text
http://localhost:8080
```

---

## Run With Docker

### 1. Create `.env`

Create a `.env` file in the project root:

```env
DB_PASSWORD=postgres
JWT_SECRET=ThisIsALongDemoJwtSecretForLocalDockerOnlyChangeMe123456789
```

### 2. Start the Application

```bash
docker compose up -d --build
```

### 3. Check Running Containers

```bash
docker compose ps
```

Expected services:

```text
hotel_booking_app
hotel_booking_postgres
```

### 4. Open Swagger

```text
http://localhost:8080/swagger-ui.html
```

### 5. Stop Containers

```bash
docker compose down
```

### 6. Stop Containers and Remove Database Volume

```bash
docker compose down -v
```

Use this only if you want to delete Docker PostgreSQL data and start from scratch.

---

## Demo Accounts

When demo data seeding is enabled, the following accounts are created:

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

## Example Login Request

```http
POST /api/auth/login
```

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

Use the token in Swagger or Postman as:

```text
Bearer jwt_token_here
```

---

## Security Notes

* Passwords are hashed using BCrypt.
* JWT secret is loaded from environment variables.
* `.env` is ignored and should not be committed.
* `.env.example` is safe to commit because it contains demo placeholders only.
* Admin endpoints require `ADMIN` role.
* User booking endpoints require authentication.

---

## Error Handling

The project includes a global exception handler for:

* Validation errors
* Invalid request body
* Invalid enum or parameter values
* Not found errors
* Conflict errors
* Unauthorized login attempts
* General unexpected errors

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

## Current Status

The project includes:

* Authentication and authorization
* Hotel APIs
* Room APIs
* Booking APIs
* Admin dashboard statistics
* Validation
* Swagger documentation
* Docker support
* Demo seed data