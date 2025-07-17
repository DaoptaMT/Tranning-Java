# Pharmacy Management System - Backend

## 📌 Overview
Pharmacy Management System Backend is a Spring Boot application designed to manage pharmacy operations, focusing on user authentication, role-based access control, and secure token management. The system ensures scalability, maintainability, and security by integrating modern technologies such as Redis, Flyway, and JPA.

## 🛠 Tech Stack
- **Java**: 21
- **Spring Boot**: 3.5.3
- **MySQL**: Relational database
- **Redis**: Token storage & caching
- **Flyway**: Database migration
- **MapStruct**: DTO ↔ Entity mapping
- **BCrypt**: Password hashing
- **Maven**: Dependency management
- **Redis**: Caching & token management

## 📂 Project Structure
```bash
src/
 ├── main/
 │   ├── java/com/mt/pharmacy_be/
 │   │   ├── config/          # Application & security configurations
 │   │   ├── controller/      # REST controllers
 │   │   ├── dto/             # Data Transfer Objects
 │   │   ├── entity/          # JPA entities
 │   │   ├── enums/           # Enum definitions
 │   │   ├── exception/       # Custom exception handling
 │   │   ├── repository/      # JPA repositories
 │   │   ├── service/         # Business logic layer
 │   │   └── util/            # Utility classes
 │   └── resources/
 │       ├── db/migration/    # Flyway migration scripts
 │       └── application.yml  # Application configuration
 └── test/                    # Unit & integration tests
```

## ⚙ Installation & Setup
### Prerequisites
- Java 21
- Maven 3.9+
- MySQL
- Redis

## Getting Started
1. Clone the repository
```bash
git clone https://github.com/phhtruc/pharmacy_be.git
cd pharmacy_be
```
2. Configure the database & Redis
```bash
yaml
spring:
datasource:
url: jdbc:mysql:your_url
username: your_username
password: your_password
redis:
host: localhost
port: 6379
Run database migrations
```

3. Build & run the application
```bash
mvn clean install
mvn spring-boot:run
```
## 📌 API Documentation
Access Swagger UI at: http://localhost:8000/swagger-ui.html

## 🗄 Database Migration
Migration scripts location: src/main/resources/db/migration

## 🤝 Contributing
1. Fork the repository

2. Create a feature branch
```bash
git checkout -b feature/your-feature
```

3. Commit & push changes

```bash
git commit -m "Add your feature"
git push origin feature/your-feature
Create a Pull Request
```
