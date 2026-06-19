# 🅿️ Smart Parking Slot Booking API

A production-style REST API built with Spring Boot, JPA, and MySQL — deployed on AWS EC2.

## 🚀 Live API
Base URL: `http://54.221.80.226:8080`

## 🛠️ Tech Stack
- Java 21
- Spring Boot 3.2
- Spring Data JPA / Hibernate
- MySQL 8.4
- Maven
- AWS EC2

## 📐 Architecture
Request → Controller → Service → Repository → MySQL Database

## 🗄️ Entity Structure
ParkingLot

└── ParkingSlot (ManyToOne)

└── Booking (ManyToOne)

└── Vehicle (ManyToOne)

## 📌 API Endpoints

### Parking Lots
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/lots` | Create a parking lot |
| GET | `/api/lots` | Get all parking lots |
| GET | `/api/lots/{id}` | Get lot by ID |

### Parking Slots
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/slots` | Create a slot |
| GET | `/api/slots` | Get all slots |
| GET | `/api/slots/{id}` | Get slot by ID |
| GET | `/api/slots/available?lotId=1&slotType=TWO_WHEELER` | Get available slots |

### Vehicles
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/vehicles` | Register a vehicle |
| GET | `/api/vehicles` | Get all vehicles |
| GET | `/api/vehicles/{id}` | Get vehicle by ID |

### Bookings
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/bookings` | Create a booking |
| PUT | `/api/bookings/{id}/cancel` | Cancel a booking |
| GET | `/api/bookings` | Get all bookings |
| GET | `/api/bookings/status/{status}` | Get bookings by status |

## ⚙️ Key Features
- **Conflict Detection** — prevents double-booking via JPQL query at DB level
- **Vehicle-Slot Type Matching** — TWO_WHEELER can only park in TWO_WHEELER slots
- **Auto-Release Scheduler** — `@Scheduled` job runs every 5 minutes, auto-releases expired bookings back to AVAILABLE
- **Global Exception Handler** — structured JSON error responses via `@RestControllerAdvice`
- **3-Layer MVC Architecture** — Controller → Service → Repository

## 📋 Sample Requests

### Create Parking Lot
```json
POST /api/lots
{
    "name": "Phoenix Mall Parking",
    "location": "Velachery, Chennai",
    "totalSlots": 50
}
```

### Create Booking
```json
POST /api/bookings
{
    "vehicleId": 1,
    "parkingSlotId": 1,
    "startTime": "2026-06-18T14:00:00",
    "endTime": "2026-06-18T16:00:00"
}
```

## 🚀 Deployment
- Hosted on **AWS EC2** (t3.micro, Ubuntu 22.04)
- MySQL 8.4 installed on EC2
- 1GB swap memory configured for stable performance
- Elastic IP assigned for consistent public access

## 👩‍💻 Author
Yuvasree V — [GitHub](https://github.com/yuvasreekrishnan) | [LinkedIn] https://www.linkedin.com/in/yuvasree-v-10153929a/
