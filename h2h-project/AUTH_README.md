# H2H Authentication API

## Overview
This application uses JWT (JSON Web Token) based authentication with Spring Security and BCrypt password hashing.

## Test Users
The application comes with pre-loaded test users (password for all: `password123`):

| Email | First Name | Last Name | Zip Code |
|-------|-----------|-----------|----------|
| john.doe@example.com | John | Doe | 12345 |
| jane.smith@example.com | Jane | Smith | 67890 |
| test.user@example.com | Test | User | 54321 |

## API Endpoints

### 1. Register New User
**POST** `/api/auth/register`

**Request Body:**
```json
{
  "email": "newuser@example.com",
  "password": "securepassword123",
  "firstName": "John",
  "lastName": "Smith",
  "zipCode": "12345"
}
```

**Success Response (201 Created):**
```json
{
  "id": 4,
  "email": "newuser@example.com",
  "firstName": "John",
  "lastName": "Smith",
  "message": "User registered successfully"
}
```

**Error Response (400 Bad Request):**
```json
"User with email newuser@example.com already exists"
```

### 2. Login
**POST** `/api/auth/login`

**Request Body:**
```json
{
  "email": "john.doe@example.com",
  "password": "password123"
}
```

**Success Response (200 OK):**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huLmRvZUBleGFtcGxlLmNvbSIsImlhdCI6MTYxMjM0NTY3OCwiZXhwIjoxNjEyNDMyMDc4fQ...",
  "email": "john.doe@example.com",
  "firstName": "John",
  "lastName": "Doe"
}
```

**Error Response (401 Unauthorized):**
```json
"Invalid email or password"
```

### 3. Register Player (Combined User + Player)
**POST** `/api/register/player`

**Request Body:**
```json
{
  "email": "player@example.com",
  "password": "password123",
  "firstName": "Player",
  "lastName": "One",
  "zipCode": "12345",
  "position": 1,
  "teamId": null
}
```

**Success Response (201 Created):**
Returns the created Player object.

## Using Protected Endpoints

All endpoints except `/api/auth/**` and `/api/register/**` require authentication.

Include the JWT token in the Authorization header:
```
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
```

**Example with curl:**
```bash
curl -H "Authorization: Bearer YOUR_TOKEN_HERE" \
     http://localhost:8080/api/teams
```

## Token Information
- **Token Type:** JWT (JSON Web Token)
- **Expiration:** 24 hours (86400000 ms)
- **Algorithm:** HS256
- **Token Prefix:** Bearer

## Security Features
- ✅ BCrypt password hashing (strength 10)
- ✅ Stateless JWT authentication
- ✅ Token-based session management
- ✅ Email uniqueness validation
- ✅ Password validation (minimum 6 characters)
- ✅ Protected endpoints
- ✅ CORS disabled by default (configure as needed)

## Development Notes

### Database
- Using H2 in-memory database
- Database is reset on each application restart
- Test users are automatically loaded from `data.sql`

### H2 Console
Access the H2 console at: http://localhost:8080/h2-console
- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `sa`
- Password: (empty)

## Quick Start

1. **Start the application**
2. **Test login with existing user:**
   ```bash
   curl -X POST http://localhost:8080/api/auth/login \
        -H "Content-Type: application/json" \
        -d '{"email":"john.doe@example.com","password":"password123"}'
   ```

3. **Save the token from the response**

4. **Use the token to access protected endpoints:**
   ```bash
   curl -H "Authorization: Bearer YOUR_TOKEN" \
        http://localhost:8080/api/teams
   ```

## Troubleshooting

### "Invalid email or password"
- Verify the email exists in the database
- Check that the password is correct (test users use "password123")
- Ensure the database has been initialized properly

### "User already exists"
- The email is already registered
- Use a different email or login with existing credentials

### "401 Unauthorized"
- Token is missing or invalid
- Token may have expired (24 hours)
- Request a new token by logging in again
