# Blog Platform REST API

Production-ready blog backend built with Spring Boot 3, MySQL, and JWT authentication. Features role-based access control, ownership validation, auto-seeding, and Swagger documentation.

## Features

**Authentication**
- Register viewer users
- Login with JWT (access + refresh tokens)
- Refresh access token
- BCrypt password encryption

**Authorization (RBAC)**
- **ADMIN**: Full CRUD on all resources, exclusive access to `/api/users/**`
- **VIEWER**: Create/update/delete own posts and comments
- **GUEST**: View posts, comments, categories (no authentication)

**Blog Management**
- CRUD for posts, categories, comments
- Post author and category included in responses
- Ownership rules (only author or admin can edit/delete)
- Slug-based post retrieval

**System**
- Global exception handler
- Unified API response structure
- Java-based auto-seeding
- Swagger UI documentation
- Stateless architecture

## Tech Stack

| Layer      | Technology                       |
| ---------- | -------------------------------- |
| Language   | Java 17                          |
| Framework  | Spring Boot 3.x                  |
| Database   | MySQL 8.x                        |
| Security   | Spring Security 6 + JWT          |
| Docs       | Springdoc OpenAPI 3 + Swagger UI |
| ORM        | JPA / Hibernate                  |
| Build Tool | Maven                            |

## Project Structure

```
src/main/java/com/ami1manna/blog
├── config/         → Swagger, PasswordEncoder, DataInitializer
├── controller/     → REST Controllers
├── dto/            → Request/Response DTOs
│   └── auth/       → Login/Register/Refresh DTOs
├── entity/         → JPA Entities
├── exception/      → GlobalExceptionHandler + Exceptions
├── mapper/         → Entity ↔ DTO Converters
├── repository/     → Spring Data JPA Repositories
├── security/       → JWT Filter, JwtUtil, SecurityConfig
└── service/        → Interfaces + Implementations
    └── impl/
```

## Setup

**1. Clone Repository**
```bash
git clone https://github.com/<your-username>/<your-repo>.git
cd <your-repo>
```

**2. Create Database**
```sql
CREATE DATABASE blog;
```

**3. Configure application.properties**
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/blog
spring.datasource.username=root
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
```

**4. Run Application**
```bash
mvn spring-boot:run
```

Expected output:
```
>>> Loading initial seed data...
>>> Seed data inserted successfully!
```

## Database Seeder

Auto-inserts initial data on startup:
- 10 Users (BCrypt-encrypted passwords)
- 10 Categories
- 10 Posts (Admin author)
- 15 Comments

Location: `com/ami1manna/blog/config/DataInitializer.java`

## Security Model

**Public Endpoints (No authentication)**
- `GET /api/posts/**`
- `GET /api/comments/**`
- `GET /api/categories/**`
- `POST /api/auth/register`
- `POST /api/auth/login`
- `POST /api/auth/refresh`

**Viewer (Authenticated)**
- Create/update/delete own posts and comments
- View own profile

**Admin**
- Full access to all resources
- Exclusive access to `/api/users/**`
- Can modify any post/comment

**Ownership Rules**: Users can only edit/delete their own posts and comments. Admin can override.

## API Documentation

**Swagger UI**
```
http://localhost:8080/swagger-ui/index.html
```

**OpenAPI Specs**
- JSON: `http://localhost:8080/v3/api-docs`
- YAML: `http://localhost:8080/v3/api-docs.yaml`

**Authenticate in Swagger**
Click **Authorize** and enter: `Bearer <your-access-token>`

## API Endpoints

### Authentication

| Method | Endpoint             | Description            |
| ------ | -------------------- | ---------------------- |
| POST   | `/api/auth/register` | Register Viewer        |
| POST   | `/api/auth/login`    | Login (returns tokens) |
| POST   | `/api/auth/refresh`  | Refresh access token   |

### Users

| Method | Endpoint          | Access       | Description |
| ------ | ----------------- | ------------ | ----------- |
| GET    | `/api/users/{id}` | Admin & Self | Get user    |
| GET    | `/api/users`      | Admin        | List users  |
| POST   | `/api/users`      | Admin        | Create user |
| DELETE | `/api/users/{id}` | Admin        | Delete user |

### Posts

| Method | Endpoint                 | Access        | Description |
| ------ | ------------------------ | ------------- | ----------- |
| GET    | `/api/posts`             | Public        | List posts  |
| GET    | `/api/posts/{id}`        | Public        | Get post    |
| GET    | `/api/posts/slug/{slug}` | Public        | Get by slug |
| POST   | `/api/posts`             | Authenticated | Create post |
| PUT    | `/api/posts/{id}`        | Owner/Admin   | Update post |
| DELETE | `/api/posts/{id}`        | Owner/Admin   | Delete post |

### Comments

| Method | Endpoint                      | Access        | Description    |
| ------ | ----------------------------- | ------------- | -------------- |
| GET    | `/api/comments/post/{postId}` | Public        | List comments  |
| POST   | `/api/comments`               | Authenticated | Create comment |
| PUT    | `/api/comments/{id}`          | Owner/Admin   | Update comment |
| DELETE | `/api/comments/{id}`          | Owner/Admin   | Delete comment |

### Categories

| Method | Endpoint               | Access | Description     |
| ------ | ---------------------- | ------ | --------------- |
| GET    | `/api/categories`      | Public | List categories |
| GET    | `/api/categories/{id}` | Public | Get category    |
| POST   | `/api/categories`      | Admin  | Create category |
| PUT    | `/api/categories/{id}` | Admin  | Update category |
| DELETE | `/api/categories/{id}` | Admin  | Delete category |

## Testing

1. **Register**: `POST /api/auth/register`
2. **Login**: `POST /api/auth/login` → Copy `accessToken`
3. **Add Header**: `Authorization: Bearer <token>`
4. **Test Endpoints**: Use Swagger UI for interactive testing

## License

MIT License
