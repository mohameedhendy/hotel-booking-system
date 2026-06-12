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

## Author

Mohamed Hendy

Backend Developer | Java & Spring Boot
