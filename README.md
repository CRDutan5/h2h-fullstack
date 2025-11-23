# H2H - Head to Head Sports Management Backend

A Spring Boot backend application for managing sports teams, players, coaches, and referees with role-based user management.

## Features

### 🔐 Role-Based User System
- **Flexible user roles**: Player, Referee, Coach
- **Clean separation**: Personal information (User) separate from role-specific data
- **Extensible design**: Easy to add new roles in the future
- **Type-safe enums**: Compile-time validation for roles and positions

### ⚽ Player Management
- Players linked to user accounts
- Type-safe position system (Goalkeeper, Defender, Midfielder, Forward)
- Team assignment (one team per player)
- Position tracking

### 👥 Team Management
- Team creation and details
- Captain assignment (must be a player)
- Roster management with size limits
- Team statistics (wins, draws, losses)
- Custom team colors and branding

### 🎯 Coach & Referee Support
- Coach and Referee role registration
- Certification level tracking
- Years of experience tracking
- Team assignment for coaches

## Technology Stack

- **Framework**: Spring Boot 3.5.6
- **Language**: Java 21
- **Build Tool**: Gradle
- **Database**: H2 (in-memory for development)
- **Data Access**: JDBC Template (raw SQL)
- **Validation**: Jakarta Validation + Hibernate Validator
- **Security**: BCrypt password encryption

## Database Schema

### Users Table
Stores personal information for all users regardless of role.

| Column | Type | Description |
|--------|------|-------------|
| id | BIGINT | Primary key |
| email | VARCHAR(255) | Unique email address |
| password | VARCHAR(255) | BCrypt encrypted password |
| first_name | VARCHAR(100) | User's first name |
| last_name | VARCHAR(100) | User's last name |
| zip_code | VARCHAR(20) | Location zip code |
| role | VARCHAR(20) | User role (PLAYER, REFEREE, COACH) |
| created_at | TIMESTAMP | Account creation timestamp |
| updated_at | TIMESTAMP | Last update timestamp |

### Players Table
Role-specific data for players.

| Column | Type | Description |
|--------|------|-------------|
| id | BIGINT | Primary key |
| user_id | BIGINT | Foreign key to users table |
| team_id | BIGINT | Foreign key to teams table (nullable) |
| position | VARCHAR(20) | Player position (GOALKEEPER, DEFENDER, MIDFIELDER, FORWARD) |

### Teams Table
Team information and statistics.

| Column | Type | Description |
|--------|------|-------------|
| id | BIGINT | Primary key |
| name | VARCHAR(255) | Team name |
| captain_id | BIGINT | Foreign key to players table |
| zip_code | VARCHAR(20) | Team location |
| stadium | VARCHAR(255) | Home stadium name |
| description | TEXT | Team description |
| logo_url | VARCHAR(255) | Team logo URL |
| home_color | VARCHAR(50) | Home jersey color |
| away_color | VARCHAR(50) | Away jersey color |
| wins | INT | Number of wins (default 0) |
| draws | INT | Number of draws (default 0) |
| losses | INT | Number of losses (default 0) |
| max_roster_size | INT | Maximum roster size |
| current_roster_size | INT | Current roster size (default 1) |
| created_at | TIMESTAMP | Team creation timestamp |
| updated_at | TIMESTAMP | Last update timestamp |

### Referees Table
Role-specific data for referees.

| Column | Type | Description |
|--------|------|-------------|
| id | BIGINT | Primary key |
| user_id | BIGINT | Foreign key to users table |
| certification_level | VARCHAR(50) | Referee certification level |
| years_experience | INT | Years of experience (default 0) |

### Coaches Table
Role-specific data for coaches.

| Column | Type | Description |
|--------|------|-------------|
| id | BIGINT | Primary key |
| user_id | BIGINT | Foreign key to users table |
| team_id | BIGINT | Foreign key to teams table (nullable) |
| certification_level | VARCHAR(50) | Coaching certification level |
| years_experience | INT | Years of experience (default 0) |

## API Endpoints

### Registration Endpoints

#### Register a Player
Creates a new user with the PLAYER role and associated player record.

**Endpoint**: `POST /api/register/player`

**Request Body**:
```json
{
  "email": "player@example.com",
  "password": "securePassword123",
  "firstName": "John",
  "lastName": "Doe",
  "zipCode": "12345",
  "position": "FORWARD",
  "teamId": null
}
```

**Valid Positions**:
- `GOALKEEPER`
- `DEFENDER`
- `MIDFIELDER`
- `FORWARD`

**Response** (201 Created):
```json
{
  "id": 1,
  "userId": 1,
  "teamId": null,
  "position": "FORWARD"
}
```

---

#### Register a Referee
Creates a new user with the REFEREE role and associated referee record.

**Endpoint**: `POST /api/register/referee`

**Request Body**:
```json
{
  "email": "referee@example.com",
  "password": "securePassword123",
  "firstName": "Jane",
  "lastName": "Smith",
  "zipCode": "12345",
  "certificationLevel": "Level 3",
  "yearsExperience": 5
}
```

**Response** (201 Created):
```json
{
  "id": 1,
  "userId": 2,
  "certificationLevel": "Level 3",
  "yearsExperience": 5
}
```

---

#### Register a Coach
Creates a new user with the COACH role and associated coach record.

**Endpoint**: `POST /api/register/coach`

**Request Body**:
```json
{
  "email": "coach@example.com",
  "password": "securePassword123",
  "firstName": "Mike",
  "lastName": "Johnson",
  "zipCode": "12345",
  "teamId": 1,
  "certificationLevel": "UEFA A",
  "yearsExperience": 10
}
```

**Response** (201 Created):
```json
{
  "id": 1,
  "userId": 3,
  "teamId": 1,
  "certificationLevel": "UEFA A",
  "yearsExperience": 10
}
```

---

### Team Endpoints

#### Create a Team
Creates a new team with a designated captain.

**Endpoint**: `POST /api/teams`

**Request Body**:
```json
{
  "name": "Thunder FC",
  "captainId": 1,
  "zipCode": "12345",
  "stadium": "Thunder Stadium",
  "description": "A competitive local team",
  "logoUrl": "https://example.com/logo.png",
  "homeColor": "Blue",
  "awayColor": "White",
  "maxRosterSize": 25
}
```

**Required Fields**:
- `name`: Team name
- `captainId`: ID of a player to be team captain
- `zipCode`: Team location
- `stadium`: Home stadium name

**Optional Fields**:
- `description`: Team description
- `logoUrl`: URL to team logo
- `homeColor`: Home jersey color
- `awayColor`: Away jersey color
- `maxRosterSize`: Maximum roster size (default handling by service)

**Response** (201 Created):
```json
{
  "id": 1,
  "name": "Thunder FC",
  "captainId": 1,
  "stadium": "Thunder Stadium",
  "logoUrl": "https://example.com/logo.png",
  "homeColor": "Blue",
  "awayColor": "White",
  "wins": 0,
  "draws": 0,
  "losses": 0
}
```

---

#### Get Team with Roster
Retrieves team details along with the full roster of players.

**Endpoint**: `GET /api/teams/{id}/with-players`

**Path Parameters**:
- `id`: Team ID

**Response** (200 OK):
```json
{
  "team": {
    "id": 1,
    "name": "Thunder FC",
    "captainId": 1,
    "stadium": "Thunder Stadium",
    "logoUrl": "https://example.com/logo.png",
    "homeColor": "Blue",
    "awayColor": "White",
    "wins": 0,
    "draws": 0,
    "losses": 0
  },
  "players": [
    {
      "playerId": 1,
      "userId": 1,
      "position": "FORWARD",
      "firstName": "John",
      "lastName": "Doe"
    },
    {
      "playerId": 2,
      "userId": 4,
      "position": "GOALKEEPER",
      "firstName": "Alice",
      "lastName": "Williams"
    }
  ]
}
```

---

## Data Models

### Position Enum
```java
public enum Position {
    GOALKEEPER(1, "Goalkeeper"),
    DEFENDER(2, "Defender"),
    MIDFIELDER(3, "Midfielder"),
    FORWARD(4, "Forward");
}
```

**Methods**:
- `fromCode(int code)`: Convert numeric code (1-4) to Position
- `fromString(String position)`: Convert string to Position (case-insensitive)
- `getCode()`: Get numeric code
- `getDisplayName()`: Get human-readable name

### UserRole Enum
```java
public enum UserRole {
    PLAYER,
    REFEREE,
    COACH;
}
```

**Methods**:
- `fromString(String role)`: Convert string to UserRole (case-insensitive)

---

## Business Rules

### Players
- ✅ Each player can only be on **one team** at a time
- ✅ Player must have a valid position (GOALKEEPER, DEFENDER, MIDFIELDER, FORWARD)
- ✅ Player must be linked to a user account

### Teams
- ✅ Every team must have exactly **one captain**
- ✅ Captain must be a player (enforced by foreign key)
- ✅ Teams have configurable roster size limits
- ✅ Teams track wins, draws, and losses

### Users
- ✅ Email must be unique across all users
- ✅ Passwords are encrypted using BCrypt
- ✅ Each user has exactly one role (PLAYER, REFEREE, or COACH)

### Coaches
- ✅ Coaches can be assigned to a team (optional)
- ✅ Coach assignments are independent of player assignments

---

## Security

- **Password Encryption**: BCrypt algorithm for secure password hashing
- **Validation**: Jakarta Validation annotations on all request DTOs
- **Database Constraints**: Foreign keys and unique constraints enforced at DB level

---

## Getting Started

### Prerequisites
- Java 21
- Gradle 8.14.3

### Running the Application

```bash
cd h2h-project
./gradlew bootRun
```

The application will start on `http://localhost:8080`

### Database Console

H2 Console is enabled for development. Access it at:
```
http://localhost:8080/h2-console
```

**Connection Details**:
- JDBC URL: `jdbc:h2:mem:testdb`
- Username: (check application.properties)
- Password: (check application.properties)

---

## Future Enhancements

- [ ] Authentication & Authorization (JWT tokens)
- [ ] Match scheduling and results
- [ ] Player statistics tracking
- [ ] Team invitations and roster management
- [ ] Referee match assignments
- [ ] League/tournament management
- [ ] Real-time notifications

---

## Architecture

### Clean Separation of Concerns

```
┌─────────────────────────────────────────┐
│           Controller Layer              │
│   (REST API Endpoints)                  │
└────────────────┬────────────────────────┘
                 │
┌────────────────▼────────────────────────┐
│           Service Layer                 │
│   (Business Logic & Transactions)       │
└────────────────┬────────────────────────┘
                 │
┌────────────────▼────────────────────────┐
│         Repository Layer                │
│   (Data Access with JDBC Template)      │
└────────────────┬────────────────────────┘
                 │
┌────────────────▼────────────────────────┐
│            Database (H2)                │
│   (users, players, teams, coaches,      │
│    referees)                            │
└─────────────────────────────────────────┘
```

### Role-Based Data Model

```
User (Personal Info)
  │
  ├── role: PLAYER ──────> Player (position, teamId)
  │
  ├── role: REFEREE ─────> Referee (certificationLevel, yearsExperience)
  │
  └── role: COACH ───────> Coach (teamId, certificationLevel, yearsExperience)
```

---

## License

This project is part of the h2h-fullstack application.
