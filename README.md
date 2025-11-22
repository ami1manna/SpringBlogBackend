# 📘 Blog Platform API — Documentation

A complete Blog Platform built in **Spring Boot 3**, with:

* JWT Authentication & Refresh Tokens
* Role-Based Authorization (Admin/Viewer)
* Clean Architecture (Entity → Repo → Service → DTO → Mapper → Controller)
* Global Exception Handling
* Database Seeding
* Swagger UI Documentation

---

## 🚀 Run Application

```bash
mvn spring-boot:run
```

App runs at:

```
http://localhost:8080
```

---

## 📚 API Documentation (Swagger)

Swagger UI:

```
http://localhost:8080/swagger-ui.html
```

OpenAPI JSON:

```
http://localhost:8080/v3/api-docs
```

OpenAPI YAML:

```
http://localhost:8080/v3/api-docs.yaml
```

---

## 🔐 Authentication (JWT)

### 1. Register

```
POST /auth/register
```

### 2. Login

```
POST /auth/login
```

Returns:

```json
{
  "accessToken": "...",
  "refreshToken": "..."
}
```

### 3. Refresh Token

```
POST /auth/refresh
```

---

## 🔒 Authorization Rules

| Endpoint                                                        | Access         |
| --------------------------------------------------------------- | -------------- |
| `/auth/**`                                                      | Public         |
| `GET /api/v1/posts/**`                                          | Public         |
| `GET /api/v1/categories/**`                                     | Public         |
| `GET /api/v1/comments/**`                                       | Public         |
| `/api/v1/users/**`                                              | Admin only     |
| Write operations (POST/PUT/DELETE) on posts/comments/categories | Authenticated  |
| Update/Delete post/comment                                      | Admin OR Owner |

---

## 🧪 Sample Test Users

| Username     | Password    | Role        |
| ------------ | ----------- | ----------- |
| Amit Manna   | password123 | ROLE_ADMIN  |
| John Viewer  | viewerpass  | ROLE_VIEWER |
| Priya Sharma | priya123    | ROLE_VIEWER |
| …            | …           | …           |

---

## 📦 Technologies Used

* Spring Boot 3
* Spring Security 6
* JWT Authentication
* Spring Data JPA + Hibernate
* MySQL
* Swagger OpenAPI 3
* ModelMapper / Manual Mapper
* Global Exception Handler
* Docker (optional)

---

 

 
