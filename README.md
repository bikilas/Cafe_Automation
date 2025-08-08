# Cafe_Automation

A modern cafe management system built with Spring Boot and Java.

## Project Overview

Kifiya Cafe is a comprehensive cafe management system that provides an efficient way to manage cafe operations, including order management, user authentication, and cafe information management.

## Features

- User Authentication and Authorization
- Cafe Management System
- Order Processing
- Email Notifications
- RESTful API Endpoints
- Docker Support
- Database Integration
- JWT Token Authentication
docker-compose up --build -d
## Tech Stack

- **Backend**: Spring Boot 3.2.2
- **Language**: Java 21
- **Database**: JPA/Hibernate
- **API Documentation**: Swagger/OpenAPI
- **Build Tool**: Maven
- **Containerization**: Docker

## Project Structure

```
src/main/java/com/kifiya/
├── controller/     # REST controllers
├── dto/           # Data Transfer Objects
├── model/         # Entity classes
├── repository/    # Database repositories
├── service/       # Business logic
└── config/        # Configuration classes
```

## Prerequisites

- Java 21 or higher
- Maven 3.8.1 or higher
- Docker (optional)

## Getting Started

### 1. Clone the Repository
```bash
git clone [repository-url]
cd kifiya-cafe-project
```

### 2. Build the Project
```bash
./mvnw clean install
```

### 3. Run the Application
```bash
./mvnw spring-boot:run
```

### 4. Using Docker
```bash
docker-compose up
```

## Configuration

The project supports multiple environments through different configuration files:
- `application-dev.yml` - Development environment
- `application-prod.yml` - Production environment

## API Documentation

Once the application is running, you can access the API documentation at:
```
http://localhost:8080/swagger-ui.html
```

## Testing

Run the tests using:
```bash
./mvnw test
```

## Contributing

1. Fork the repository
2. Create your feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Support

For support, please contact the project maintainers or open an issue in the repository.
