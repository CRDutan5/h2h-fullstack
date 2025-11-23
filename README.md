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
- Join and leave teams dynamically

### 👥 Team Management
- Team creation and details
- Captain assignment (must be a player)
- **Dynamic roster management** (add/remove players)
- **Roster size validation** (enforces max roster limits)
- Team statistics (wins, draws, losses)
- Custom team colors and branding
- **Captain protection** (cannot remove captain from team)

### 🎯 Coach & Referee Support
- Coach and Referee role registration
- Certification level tracking
- Years of experience tracking
- Team assignment for coaches

### 🎲 Mock Data
- Pre-loaded test data for quick development
- 2 complete teams with rosters
- 10 users across all roles
- All passwords: `password123`

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

---

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
    "wins": 3,
    "draws": 1,
    "losses": 0
  },
  "players": [
    {
      "playerId": 1,
      "userId": 1,
      "position": "FORWARD",
      "firstName": "John",
      "lastName": "Striker"
    },
    {
      "playerId": 2,
      "userId": 2,
      "position": "GOALKEEPER",
      "firstName": "Mike",
      "lastName": "Keeper"
    }
  ]
}
```

---

#### Add Player to Team
Adds a player to a team's roster. Validates roster size limits and ensures player is not already on another team.

**Endpoint**: `POST /api/teams/{teamId}/players/{playerId}`

**Path Parameters**:
- `teamId`: ID of the team
- `playerId`: ID of the player to add

**Validations**:
- ✅ Player must exist
- ✅ Player cannot already be on another team
- ✅ Team roster must not be full
- ✅ Automatically updates roster count

**Example Request**:
```bash
POST /api/teams/1/players/5
```

**Response** (200 OK):
```json
"Player added to team successfully"
```

**Error Responses**:
- `400 Bad Request`: "Player is already on a team. Players can only be on one team at a time."
- `400 Bad Request`: "Team roster is full. Max size: 25"
- `404 Not Found`: "Player not found with id: 5"
- `404 Not Found`: "Team not found with id: 1"

---

#### Remove Player from Team
Removes a player from a team's roster. Cannot remove the team captain.

**Endpoint**: `DELETE /api/teams/{teamId}/players/{playerId}`

**Path Parameters**:
- `teamId`: ID of the team
- `playerId`: ID of the player to remove

**Validations**:
- ✅ Player must exist and be on the team
- ✅ Cannot remove the team captain
- ✅ Automatically updates roster count

**Example Request**:
```bash
DELETE /api/teams/1/players/5
```

**Response** (200 OK):
```json
"Player removed from team successfully"
```

**Error Responses**:
- `400 Bad Request`: "Player is not on this team"
- `400 Bad Request`: "Cannot remove the team captain. Assign a new captain first."
- `404 Not Found`: "Player not found with id: 5"
- `404 Not Found`: "Team not found with id: 1"

---

## Mock Data

The application comes pre-loaded with mock data for quick testing and development.

### Users (Password: `password123` for all)

| ID | Email | Role | Name |
|----|-------|------|------|
| 1 | john.striker@example.com | PLAYER | John Striker |
| 2 | mike.keeper@example.com | PLAYER | Mike Keeper |
| 3 | sarah.defender@example.com | PLAYER | Sarah Defender |
| 4 | alex.mid@example.com | PLAYER | Alex Midfielder |
| 5 | emma.forward@example.com | PLAYER | Emma Forward |
| 6 | coach.jones@example.com | COACH | Tom Jones |
| 7 | ref.smith@example.com | REFEREE | Jane Smith |
| 8 | lily.goalie@example.com | PLAYER | Lily Guardian |
| 9 | chris.back@example.com | PLAYER | Chris Backfield |
| 10 | coach.williams@example.com | COACH | Lisa Williams |

### Teams

#### Thunder FC (Team ID: 1)
- **Captain**: John Striker (#1)
- **Roster**: 4 players
  - John Striker (FORWARD) - Captain
  - Mike Keeper (GOALKEEPER)
  - Sarah Defender (DEFENDER)
  - Alex Midfielder (MIDFIELDER)
- **Coach**: Tom Jones (UEFA A License, 12 years)
- **Stadium**: Thunder Stadium
- **Colors**: Blue (Home), White (Away)
- **Record**: 3W-1D-0L
- **Max Roster Size**: 25

#### Lightning United (Team ID: 2)
- **Captain**: Emma Forward (#5)
- **Roster**: 3 players
  - Emma Forward (FORWARD) - Captain
  - Lily Guardian (GOALKEEPER)
  - Chris Backfield (DEFENDER)
- **Coach**: Lisa Williams (UEFA B License, 8 years)
- **Stadium**: Lightning Arena
- **Colors**: Yellow (Home), Black (Away)
- **Record**: 2W-2D-1L
- **Max Roster Size**: 20

### Referees
- **Jane Smith** - FIFA Level 3, 10 years experience

### Quick Test Examples

```bash
# Login with mock user
POST /api/register/player
{
  "email": "john.striker@example.com",
  "password": "password123"
}

# Get Thunder FC roster
GET /api/teams/1/with-players

# Get Lightning United roster
GET /api/teams/2/with-players

# Move a player between teams (first remove from current team)
DELETE /api/teams/1/players/4
POST /api/teams/2/players/4
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
- ✅ Players can join and leave teams dynamically

### Teams
- ✅ Every team must have exactly **one captain**
- ✅ Captain must be a player (enforced by foreign key)
- ✅ **Captain cannot be removed** from the team
- ✅ Teams have configurable roster size limits
- ✅ **Roster is validated** before adding players
- ✅ Roster count automatically updates on add/remove
- ✅ Teams track wins, draws, and losses

### Roster Management
- ✅ Centralized in **TeamService** for easy extension
- ✅ **Transactional operations** ensure data consistency
- ✅ Validates all business rules before modifying rosters
- ✅ Ready for coach assignment extension

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
- **Transaction Management**: @Transactional ensures data consistency

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

### Using Mock Data

The application automatically loads mock data on startup from `data.sql`. You can:
- Test with pre-existing teams and players
- Use any mock user email with password `password123`
- Immediately test roster management features
- View complete team rosters with the `/with-players` endpoint

---

## Future Enhancements

- [ ] Authentication & Authorization (JWT tokens)
- [ ] Match scheduling and results
- [ ] Player statistics tracking
- [x] ~~Team roster management~~ ✅ **Implemented**
- [ ] Team invitations system
- [ ] Referee match assignments
- [ ] League/tournament management
- [ ] Real-time notifications
- [ ] Captain transfer functionality
- [ ] Coach team assignments (extend current roster system)

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
│   - Roster Management (TeamService)     │
│   - Validations & Business Rules        │
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

### Roster Management Flow

```
TeamController.addPlayerToTeam()
        │
        ▼
TeamService.addPlayerToTeam() [@Transactional]
        │
        ├──> Validate team exists
        ├──> Validate player exists
        ├──> Check player not on another team
        ├──> Check roster not full
        │
        ├──> PlayerRepository.updatePlayerTeamId()
        └──> TeamRepository.updateRosterSize()
```

**Why TeamService for Roster Management?**
- Team owns the roster (centralized control)
- Enforces team-level business rules
- **Future-ready**: When coaches join teams, same service handles it
- Transactional consistency across repositories

---

## License

This project is part of the h2h-fullstack application.
