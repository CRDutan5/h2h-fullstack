# H2H Sports Platform - API Documentation

## Overview
A comprehensive sports platform management system built with Spring Boot, featuring JWT authentication and support for managing users, players, coaches, and referees.

## Table of Contents
- [Technology Stack](#technology-stack)
- [Database Schema](#database-schema)
- [Authentication](#authentication)
- [API Endpoints](#api-endpoints)
- [Sample Data](#sample-data)
- [Getting Started](#getting-started)

---

## Technology Stack

- **Framework**: Spring Boot 3.5.6
- **Language**: Java 21
- **Database**: H2 (in-memory)
- **Security**: Spring Security + JWT
- **Password Hashing**: BCrypt
- **Build Tool**: Gradle
- **ORM**: JDBC Template (No JPA/Hibernate)

---

## Database Schema

### Tables

#### `users`
Core user authentication and profile information.

| Column | Type | Constraints |
|--------|------|-------------|
| id | BIGINT | PRIMARY KEY, AUTO_INCREMENT |
| email | VARCHAR(255) | UNIQUE, NOT NULL |
| password | VARCHAR(255) | NOT NULL (BCrypt hashed) |
| first_name | VARCHAR(100) | NOT NULL |
| last_name | VARCHAR(100) | NOT NULL |
| created_at | TIMESTAMP | NOT NULL, DEFAULT CURRENT_TIMESTAMP |
| updated_at | TIMESTAMP | NOT NULL, DEFAULT CURRENT_TIMESTAMP |

#### `players`
Player-specific information and statistics.

| Column | Type | Constraints |
|--------|------|-------------|
| id | BIGINT | PRIMARY KEY, AUTO_INCREMENT |
| user_id | BIGINT | UNIQUE, NOT NULL, FK → users(id) |
| position | VARCHAR(50) | NOT NULL |
| skill_level | VARCHAR(20) | NOT NULL |
| years_experience | INT | DEFAULT 0 |
| bio | TEXT | - |
| created_at | TIMESTAMP | NOT NULL, DEFAULT CURRENT_TIMESTAMP |

#### `coaches`
Coach-specific information and credentials.

| Column | Type | Constraints |
|--------|------|-------------|
| id | BIGINT | PRIMARY KEY, AUTO_INCREMENT |
| user_id | BIGINT | UNIQUE, NOT NULL, FK → users(id) |
| certification_level | VARCHAR(50) | NOT NULL |
| years_experience | INT | DEFAULT 0 |
| specialization | VARCHAR(100) | - |
| bio | TEXT | - |
| created_at | TIMESTAMP | NOT NULL, DEFAULT CURRENT_TIMESTAMP |

#### `referees`
Referee-specific information and credentials.

| Column | Type | Constraints |
|--------|------|-------------|
| id | BIGINT | PRIMARY KEY, AUTO_INCREMENT |
| user_id | BIGINT | UNIQUE, NOT NULL, FK → users(id) |
| certification_level | VARCHAR(50) | NOT NULL |
| years_experience | INT | DEFAULT 0 |
| bio | TEXT | - |
| created_at | TIMESTAMP | NOT NULL, DEFAULT CURRENT_TIMESTAMP |

---

## Authentication

### JWT Configuration
- **Token Expiration**: 24 hours
- **Algorithm**: HS256
- **Secret**: Configured in `application.properties`

### Security Flow
1. User registers via `/api/auth/register`
2. User logs in via `/api/auth/login` → Receives JWT token
3. Include token in subsequent requests: `Authorization: Bearer <token>`
4. Protected endpoints validate JWT before allowing access

### Password Security
- **Algorithm**: BCrypt with strength 10
- **Salt**: Automatically generated per password
- Passwords are never stored in plain text

---

## API Endpoints

### Authentication Endpoints

#### Register New User
```
POST /api/auth/register
```

**Request Body:**
```json
{
  "email": "user@example.com",
  "password": "password123",
  "firstName": "John",
  "lastName": "Doe"
}
```

**Success Response (201):**
```json
{
  "id": 1,
  "email": "user@example.com",
  "firstName": "John",
  "lastName": "Doe",
  "message": "User registered successfully"
}
```

**Error Responses:**
- `400` - User already exists
- `400` - Validation error

---

#### Login
```
POST /api/auth/login
```

**Request Body:**
```json
{
  "email": "user@example.com",
  "password": "password123"
}
```

**Success Response (200):**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "email": "user@example.com",
  "firstName": "John",
  "lastName": "Doe"
}
```

**Error Responses:**
- `401` - Invalid credentials
- `500` - Internal server error

---

### Player Endpoints
All endpoints require authentication (JWT token in header).

#### Create Player Profile
```
POST /api/players
Authorization: Bearer <token>
```

**Request Body:**
```json
{
  "userId": 1,
  "position": "Forward",
  "skillLevel": "Advanced",
  "yearsExperience": 5,
  "bio": "Experienced player with strong scoring abilities"
}
```

**Success Response (201):**
```json
{
  "id": 1,
  "userId": 1,
  "position": "Forward",
  "skillLevel": "Advanced",
  "yearsExperience": 5,
  "bio": "Experienced player with strong scoring abilities",
  "createdAt": "2024-11-30T12:00:00",
  "email": "user@example.com",
  "firstName": "John",
  "lastName": "Doe"
}
```

#### Get Player by ID
```
GET /api/players/{id}
Authorization: Bearer <token>
```

#### Get Player by User ID
```
GET /api/players/user/{userId}
Authorization: Bearer <token>
```

#### Get All Players
```
GET /api/players
Authorization: Bearer <token>
```

#### Update Player
```
PUT /api/players/{id}
Authorization: Bearer <token>
```

**Request Body:** (userId is not updatable)
```json
{
  "position": "Midfielder",
  "skillLevel": "Expert",
  "yearsExperience": 6,
  "bio": "Updated bio"
}
```

#### Delete Player
```
DELETE /api/players/{id}
Authorization: Bearer <token>
```

**Success Response:** `204 No Content`

---

### Coach Endpoints
All endpoints require authentication (JWT token in header).

#### Create Coach Profile
```
POST /api/coaches
Authorization: Bearer <token>
```

**Request Body:**
```json
{
  "userId": 2,
  "certificationLevel": "Level 3 UEFA",
  "yearsExperience": 8,
  "specialization": "Youth Development",
  "bio": "Passionate about developing young talent"
}
```

#### Get Coach by ID
```
GET /api/coaches/{id}
Authorization: Bearer <token>
```

#### Get Coach by User ID
```
GET /api/coaches/user/{userId}
Authorization: Bearer <token>
```

#### Get All Coaches
```
GET /api/coaches
Authorization: Bearer <token>
```

#### Update Coach
```
PUT /api/coaches/{id}
Authorization: Bearer <token>
```

#### Delete Coach
```
DELETE /api/coaches/{id}
Authorization: Bearer <token>
```

---

### Referee Endpoints
All endpoints require authentication (JWT token in header).

#### Create Referee Profile
```
POST /api/referees
Authorization: Bearer <token>
```

**Request Body:**
```json
{
  "userId": 3,
  "certificationLevel": "FIFA Certified",
  "yearsExperience": 10,
  "bio": "International referee with experience in major tournaments"
}
```

#### Get Referee by ID
```
GET /api/referees/{id}
Authorization: Bearer <token>
```

#### Get Referee by User ID
```
GET /api/referees/user/{userId}
Authorization: Bearer <token>
```

#### Get All Referees
```
GET /api/referees
Authorization: Bearer <token>
```

#### Update Referee
```
PUT /api/referees/{id}
Authorization: Bearer <token>
```

#### Delete Referee
```
DELETE /api/referees/{id}
Authorization: Bearer <token>
```

---

## Sample Data

The application comes pre-loaded with test data (password for all: `password123`):

### Users
| ID | Email | First Name | Last Name | Role |
|----|-------|-----------|-----------|------|
| 1 | john.player@example.com | John | Player | Player |
| 2 | jane.coach@example.com | Jane | Coach | Coach |
| 3 | mike.referee@example.com | Mike | Referee | Referee |
| 4 | sarah.player@example.com | Sarah | Wilson | Player |
| 5 | tom.coach@example.com | Tom | Anderson | Coach |

### Players
- **John Player** - Forward, Advanced, 5 years experience
- **Sarah Wilson** - Midfielder, Intermediate, 3 years experience

### Coaches
- **Jane Coach** - Level 3 UEFA, Youth Development, 8 years experience
- **Tom Anderson** - Level 2 USSF, Tactical Training, 4 years experience

### Referees
- **Mike Referee** - FIFA Certified, 10 years experience

---

## Getting Started

### Prerequisites
- Java 21
- Gradle 8.x

### Running the Application

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd h2h-project
   ```

2. **Build the project**
   ```bash
   ./gradlew build
   ```

3. **Run the application**
   ```bash
   ./gradlew bootRun
   ```

4. **Application will start on**
   ```
   http://localhost:8080
   ```

### Quick Test

1. **Login with test user:**
   ```bash
   curl -X POST http://localhost:8080/api/auth/login \
     -H "Content-Type: application/json" \
     -d '{
       "email": "john.player@example.com",
       "password": "password123"
     }'
   ```

2. **Save the token from response**

3. **Access protected endpoint:**
   ```bash
   curl -H "Authorization: Bearer YOUR_TOKEN" \
     http://localhost:8080/api/players
   ```

### H2 Console Access

The H2 database console is available at:
```
http://localhost:8080/h2-console
```

**Connection Settings:**
- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `sa`
- Password: (empty)

---

## API Response Codes

| Code | Description |
|------|-------------|
| 200 | Success |
| 201 | Created |
| 204 | No Content (successful deletion) |
| 400 | Bad Request (validation error) |
| 401 | Unauthorized (invalid credentials or token) |
| 404 | Not Found |
| 500 | Internal Server Error |

---

## Security Notes

- All passwords are hashed with BCrypt before storage
- JWT tokens expire after 24 hours
- Each user can only have ONE profile (player, coach, or referee)
- Deleting a user cascades to delete their profile
- All endpoints except `/api/auth/**` require authentication

---

## Project Structure

```
src/main/java/com/example/h2h_project/
├── controller/          # REST API endpoints
│   ├── AuthController.java
│   ├── PlayerController.java
│   ├── CoachController.java
│   └── RefereeController.java
├── dto/                 # Data Transfer Objects
│   ├── LoginRequest.java
│   ├── LoginResponse.java
│   ├── RegisterRequest.java
│   ├── RegisterResponse.java
│   ├── PlayerRequest.java
│   ├── CoachRequest.java
│   └── RefereeRequest.java
├── model/               # Domain models
│   ├── User.java
│   ├── Player.java
│   ├── Coach.java
│   └── Referee.java
├── repository/          # Database access layer
│   ├── UserRepository.java
│   ├── PlayerRepository.java
│   ├── CoachRepository.java
│   └── RefereeRepository.java
├── security/            # Security configuration
│   ├── SecurityConfig.java
│   ├── JwtUtil.java
│   ├── JwtAuthenticationFilter.java
│   └── CustomUserDetailsService.java
└── service/             # Business logic
    ├── UserService.java
    ├── PlayerService.java
    ├── CoachService.java
    └── RefereeService.java
```

---

## Future Enhancements

- [ ] User roles and permissions
- [ ] Team management
- [ ] Match scheduling
- [ ] Statistics and analytics
- [ ] File uploads (profile pictures, documents)
- [ ] Email verification
- [ ] Password reset functionality
- [ ] OAuth2 integration

---

## License

This project is licensed under the MIT License.

---

## Contact

For questions or support, please contact the development team.
