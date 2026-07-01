# pg-management

Multi-tenant Paying Guest (PG) management platform — single Spring Boot application.

## Project Structure

```
pg-management/
├── pom.xml
├── Dockerfile
├── docker-compose.yml
└── src/main/
    ├── java/com/pgmanagement/
    │   ├── PgManagementApplication.java
    │   ├── auth/
    │   ├── user/
    │   ├── hostel/
    │   ├── guest/
    │   └── billing/
    └── resources/
        ├── application.yml
        └── db/changelog/
            ├── db.changelog-master.xml
            └── schema.sql
```

## Tech Stack

- Java 17 | Spring Boot 3.2 | Spring Security + JWT
- PostgreSQL 16 | Liquibase
- Docker | Docker Compose

## Quick Start

### Local

```bash
docker compose up postgres -d
mvn spring-boot:run
```

### Docker Compose

```bash
docker compose up -d
```

API: `http://localhost:8080`

| Service       | Port |
|---------------|------|
| pg-management | 8080 |
| PostgreSQL    | 5432 |

## API Examples

```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username":"owner1","password":"pass123","role":"OWNER","tenantId":1}'

curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"owner1","password":"pass123"}'
```

## License

MIT
