# PG Platform - Multi-tenant PG Management

A **startup-level** multi-tenant Paying Guest (PG) management platform built with microservices architecture.

## Architecture

```
pg-platform/
├── discovery-service      # Netflix Eureka - Service Discovery
├── api-gateway            # Spring Cloud Gateway - API Gateway
├── auth-service           # Login, JWT, Password encryption
├── user-service           # Admin, Warden, Owner profiles
├── hostel-service         # Hostels, Floors, Rooms, Rent config
├── guest-service          # Guests, Room assignment, Check-in/out
├── billing-service        # Invoices, Payments, Revenue reports
└── notification-service   # WhatsApp/Email reminders (placeholder)
```

## Tech Stack

- **Java 17** | **Spring Boot 3.2** | **Spring Cloud 2023.0**
- **Netflix Eureka** - Service Discovery
- **Spring Cloud Gateway** - API Gateway
- **Spring Security + JWT** - Authentication
- **MySQL** - Database (one DB per service)
- **Docker** - Containerization

## Database Schema (One Service = One Database)

| Service | Database |
|---------|----------|
| auth-service | auth_db |
| user-service | user_db |
| hostel-service | hostel_db |
| guest-service | guest_db |
| billing-service | billing_db |

## Roles

| Role | Permissions |
|------|-------------|
| **OWNER** | Create hostels, view revenue, create admin/warden |
| **ADMIN** | Manage rooms, manage guests |
| **WARDEN** | Assign rooms, check-in/check-out, update guest data |
| **ACCOUNTANT** | Manage payments |

## Quick Start

### Prerequisites

- Java 17+
- Maven 3.8+
- MySQL 8.0+
- Docker & Docker Compose (optional)

### 1. Local Development

**Create databases:**
```sql
CREATE DATABASE auth_db;
CREATE DATABASE user_db;
CREATE DATABASE hostel_db;
CREATE DATABASE guest_db;
CREATE DATABASE billing_db;
```

**Start services in order (from pg-platform root):**
```bash
# 1. Discovery Service (Eureka)
./mvnw spring-boot:run -pl discovery-service

# 2. API Gateway (new terminal)
./mvnw spring-boot:run -pl api-gateway

# 3. Auth Service (new terminal)
./mvnw spring-boot:run -pl auth-service

# 4. User, Hostel, Guest, Billing, Notification (each in new terminal)
./mvnw spring-boot:run -pl user-service
./mvnw spring-boot:run -pl hostel-service
./mvnw spring-boot:run -pl guest-service
./mvnw spring-boot:run -pl billing-service
./mvnw spring-boot:run -pl notification-service
```

**Or build & run all from root:**
```bash
./mvnw clean install
./mvnw spring-boot:run -pl discovery-service &
# Wait for Eureka to start, then:
./mvnw spring-boot:run -pl api-gateway &
./mvnw spring-boot:run -pl auth-service &
# ... etc
```

### 2. Docker Compose

```bash
# From pg-platform root
docker-compose up -d

# Check Eureka dashboard
open http://localhost:8761
```

### 3. Ports

| Service | Port |
|---------|------|
| API Gateway | 8080 |
| Eureka | 8761 |
| Auth | 8081 |
| User | 8082 |
| Hostel | 8083 |
| Guest | 8084 |
| Billing | 8085 |
| Notification | 8086 |

## API Usage

All requests go through **API Gateway** at `http://localhost:8080`.

### Register & Login
```bash
# Register first owner (tenantId = 1)
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username":"owner1","password":"pass123","role":"OWNER","tenantId":1}'

# Login
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"owner1","password":"pass123"}'
```

### Create Hostel (use JWT token)
```bash
curl -X POST http://localhost:8080/api/hostels \
  -H "Authorization: Bearer <token>" \
  -H "Content-Type: application/json" \
  -d '{"tenantId":1,"ownerId":1,"name":"PG Palace","city":"Bangalore","state":"Karnataka"}'
```

## Future Enhancements

- [ ] Online payment (Razorpay/Stripe)
- [ ] WhatsApp reminder integration
- [ ] Automatic monthly invoice generation
- [ ] Occupancy dashboard
- [ ] Revenue analytics
- [ ] Mobile app support
- [ ] JWT validation filter in API Gateway

## License

MIT
