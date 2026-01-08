# Task API - Spring Boot Backend

A production-ready REST API for managing tasks and projects with user authentication and MongoDB.

## Table of Contents
- [Features](#features)
- [Tech Stack](#tech-stack)
- [Architecture](#architecture)
- [Installation](#installation)
- [API Endpoints](#api-endpoints)
- [Security](#security)
- [Docker](#docker)

## Features

✅ Task Management (Create, Read, Update, Delete)
✅ Project Organization
✅ User Authentication with JWT
✅ Role-Based Access Control
✅ Activity Logging & Audit Trail
✅ Task Filtering & Pagination

## Tech Stack

| Component | Technology |
|-----------|----------|
| **Framework** | Spring Boot 3.x |
| **Language** | Java 17+ |
| **Database** | MongoDB |
| **Security** | Spring Security + JWT |
| **Build** | Maven |

## Architecture

### System Flow
```
Client → Spring Boot API → MongoDB
  ↓
Controller Layer → Service Layer → Repository Layer → MongoDB
  ↓
JWT Filter → Spring Security → Authorization Check
```

### Request Flow
```
1. Client sends request with JWT token
2. JwtAuthenticationFilter validates token
3. Spring Security checks authorization
4. Controller processes request
5. Service executes business logic
6. Repository performs MongoDB queries
7. Response returned to client
```

## Installation

### 1. Clone Repository
```bash
git clone https://github.com/Shavez90/Task-api.git
cd Task-api
```

### 2. Setup MongoDB
```bash
# Local MongoDB
mongod

# Or use MongoDB Compass
# Connection: mongodb://localhost:27017
```

### 3. Configure Application
Create `src/main/resources/application.properties`:
```properties
spring.data.mongodb.uri=mongodb://localhost:27017/task_db
jwt.secret=your_secret_key_here
jwt.expiration=86400000
server.port=8080
server.servlet.context-path=/api
```

### 4. Build & Run
```bash
mvn clean install
mvn spring-boot:run
```

Verify: `curl http://localhost:8080/api/health`

## Project Structure

```
Task-api/
├── src/main/java/com/example/template/
│   ├── controller/     # REST endpoints
│   ├── service/        # Business logic
│   ├── repository/     # MongoDB queries
│   ├── entity/         # Data models
│   ├── dto/            # Data transfer objects
│   ├── security/       # JWT filter
│   ├── exception/      # Error handling
│   └── config/         # Configuration
├── src/main/resources/
│   └── application.properties
├── pom.xml
└── docker-compose.yml
```

## API Endpoints

### Authentication
| Method | Endpoint | Description |
|--------|----------|----------|
| POST | `/auth/register` | Register user |
| POST | `/auth/login` | Login user |
| POST | `/auth/logout` | Logout |
| POST | `/auth/refresh` | Refresh token |

### Tasks
| Method | Endpoint | Description |
|--------|----------|----------|
| GET | `/tasks` | Get all tasks |
| GET | `/tasks/{id}` | Get task |
| POST | `/tasks` | Create task |
| PUT | `/tasks/{id}` | Update task |
| DELETE | `/tasks/{id}` | Delete task |

### Projects
| Method | Endpoint | Description |
|--------|----------|----------|
| GET | `/projects` | Get all projects |
| POST | `/projects` | Create project |
| PUT | `/projects/{id}` | Update project |
| DELETE | `/projects/{id}` | Delete project |

### Users
| Method | Endpoint | Description |
|--------|----------|----------|
| GET | `/users/profile` | Get profile |
| PUT | `/users/profile` | Update profile |

## Security

🔐 **Authentication**: JWT tokens signed with HS256
🔐 **Password**: BCrypt hashing with 10 salt rounds
🔐 **Authorization**: Role-based access control (@PreAuthorize)
🔐 **Validation**: Input validation on all endpoints
🔐 **Soft Delete**: Inactive records preserved in database

## Docker

### Docker Compose
```bash
docker-compose up -d      # Start MongoDB + App
docker-compose down       # Stop services
```

### Manual Docker
```bash
mvn clean package
docker build -t task-api:latest .
docker run -d -p 8080:8080 task-api:latest
```

## Testing

```bash
mvn test                           # Run all tests
mvn test -Dtest=TaskControllerTest # Run specific test
mvn test jacoco:report             # With coverage
```

## Troubleshooting

| Issue | Solution |
|-------|----------|
| MongoDB not connecting | Check `mongod` is running |
| JWT token invalid | Verify Authorization header format |
| Port 8080 in use | Change `server.port` in properties |
| Build fails | Run `mvn clean install -U` |

## Contributing

1. Fork repo
2. Create branch: `git checkout -b feature/name`
3. Commit: `git commit -m "feat: description"`
4. Push: `git push origin feature/name`
5. Create PR

## License

MIT License

## Contact

**GitHub**: https://github.com/Shavez90/Task-api

---

**Version**: 1.0.0 | **Built with**: Spring Boot 3.x + MongoDB + JWT