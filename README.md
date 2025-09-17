# Online Food Delivery System - Spring Boot Microservices

A comprehensive microservices-based online food delivery platform built with Spring Boot, Spring Cloud, and modern development practices.

## Architecture Overview

This system consists of the following microservices:

### Core Services
- **Eureka Discovery Server** (Port 8761) - Service registry and discovery
- **API Gateway** (Port 8080) - Single entry point for all client requests
- **User Service** (Port 8081) - User management and JWT authentication
- **Restaurant Service** (Port 8082) - Restaurant and menu management
- **Order Service** (Port 8083) - Order processing and management

## Features

### User Service
- User registration and authentication
- JWT token-based security
- Role-based access control (CUSTOMER, RESTAURANT_OWNER, ADMIN)
- Password encryption with BCrypt
- User profile management

### Restaurant Service
- Restaurant CRUD operations
- Menu item management with categories
- Restaurant search and filtering
- Status management (ACTIVE, INACTIVE, SUSPENDED)
- Rating and review system
- Owner-based restaurant management

### Order Service
- Order creation and management
- Inter-service communication using Feign clients
- Order status tracking (PENDING → CONFIRMED → PREPARING → OUT_FOR_DELIVERY → DELIVERED)
- Order validation and business logic
- Order history and analytics

### Infrastructure
- Service discovery with Eureka
- Load balancing and routing with Spring Cloud Gateway
- H2 in-memory database for development
- MySQL support for production
- Comprehensive error handling and validation
- Extensive logging and monitoring

## Technology Stack

- **Framework**: Spring Boot 2.7.0
- **Microservices**: Spring Cloud 2021.0.3
- **Security**: Spring Security with JWT
- **Database**: H2 (development), MySQL (production)
- **Service Discovery**: Netflix Eureka
- **API Gateway**: Spring Cloud Gateway
- **Inter-Service Communication**: OpenFeign
- **Testing**: JUnit 5, Mockito
- **Build Tool**: Maven
- **Java Version**: 11

## Prerequisites

- Java 11 or higher
- Maven 3.6 or higher
- MySQL (optional, for production)

## Getting Started

### 1. Clone the Repository
```bash
git clone <repository-url>
cd online-food-delivery-system
```

### 2. Build the Project
```bash
mvn clean install
```

### 3. Start the Services

#### Option A: Start All Services at Once
```bash
# Start Eureka Server first
cd eureka-server
mvn spring-boot:run

# Wait for Eureka to start, then start other services in separate terminals
cd ../api-gateway
mvn spring-boot:run

cd ../user-service
mvn spring-boot:run

cd ../restaurant-service
mvn spring-boot:run

cd ../order-service
mvn spring-boot:run
```

#### Option B: Using Docker Compose (if available)
```bash
docker-compose up
```

### 4. Verify Services

Check if all services are registered with Eureka:
- Open http://localhost:8761 in your browser
- Verify all services (api-gateway, user-service, restaurant-service, order-service) are listed

### 5. Access the Services

- **API Gateway**: http://localhost:8080
- **Eureka Dashboard**: http://localhost:8761
- **User Service**: http://localhost:8081
- **Restaurant Service**: http://localhost:8082
- **Order Service**: http://localhost:8083

## API Documentation

### User Service Endpoints

#### Authentication
- `POST /api/users/register` - Register a new user
- `POST /api/users/login` - Login and get JWT token

#### User Management
- `GET /api/users/profile` - Get current user profile (requires JWT)
- `GET /api/users` - Get all users (requires JWT)
- `GET /api/users/{id}` - Get user by ID
- `PUT /api/users/{id}` - Update user
- `DELETE /api/users/{id}` - Delete user

### Restaurant Service Endpoints

#### Restaurant Management
- `POST /api/restaurants` - Create restaurant
- `GET /api/restaurants` - Get all restaurants
- `GET /api/restaurants/active` - Get active restaurants
- `GET /api/restaurants/{id}` - Get restaurant by ID
- `GET /api/restaurants/owner/{ownerId}` - Get restaurants by owner
- `GET /api/restaurants/search?keyword={keyword}` - Search restaurants
- `PUT /api/restaurants/{id}` - Update restaurant
- `PATCH /api/restaurants/{id}/status?status={status}` - Update restaurant status
- `DELETE /api/restaurants/{id}` - Delete restaurant

#### Menu Management
- `POST /api/restaurants/{restaurantId}/menu-items` - Add menu item
- `GET /api/restaurants/{restaurantId}/menu-items` - Get all menu items
- `GET /api/restaurants/{restaurantId}/menu-items/available` - Get available menu items
- `GET /api/restaurants/{restaurantId}/menu-items/{itemId}` - Get menu item by ID
- `PUT /api/restaurants/{restaurantId}/menu-items/{itemId}` - Update menu item
- `PATCH /api/restaurants/{restaurantId}/menu-items/{itemId}/status` - Update menu item status
- `DELETE /api/restaurants/{restaurantId}/menu-items/{itemId}` - Delete menu item

### Order Service Endpoints

#### Order Management
- `POST /api/orders` - Create new order
- `GET /api/orders` - Get all orders
- `GET /api/orders/{id}` - Get order by ID
- `GET /api/orders/user/{userId}` - Get orders by user
- `GET /api/orders/restaurant/{restaurantId}` - Get orders by restaurant
- `GET /api/orders/status/{status}` - Get orders by status

#### Order Status Management
- `PUT /api/orders/{id}/confirm` - Confirm order
- `PUT /api/orders/{id}/prepare` - Start preparing order
- `PUT /api/orders/{id}/dispatch` - Dispatch order for delivery
- `PUT /api/orders/{id}/deliver` - Mark order as delivered
- `PATCH /api/orders/{id}/cancel` - Cancel order

## Testing

### Running Unit Tests
```bash
mvn test
```

### Running Integration Tests
```bash
mvn verify
```

### API Testing with Postman

1. Import the provided Postman collection: `postman-collection.json`
2. Set the environment variables:
   - `gateway_url`: http://localhost:8080
   - `jwt_token`: (obtained from login response)

3. Test the workflows:
   - Register a user
   - Login to get JWT token
   - Create a restaurant
   - Add menu items
   - Create an order
   - Update order status

## Database Configuration

### Development (H2)
The services are configured to use H2 in-memory database by default. Access the H2 console:
- **User Service**: http://localhost:8081/h2-console
- **Restaurant Service**: http://localhost:8082/h2-console
- **Order Service**: http://localhost:8083/h2-console

### Production (MySQL)
Update the `application.yml` files in each service:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/fooddelivery_users
    username: your_username
    password: your_password
    driver-class-name: com.mysql.cj.jdbc.Driver
  jpa:
    hibernate:
      ddl-auto: update
    properties:
      hibernate:
        dialect: org.hibernate.dialect.MySQL8Dialect
```

## Monitoring and Health Checks

- **Eureka Dashboard**: http://localhost:8761
- **Service Health**: http://localhost:808{1,2,3}/actuator/health
- **Service Info**: http://localhost:808{1,2,3}/actuator/info

## Security

### JWT Configuration
- Secret key: Configured in `user-service/application.yml`
- Token expiration: 24 hours (configurable)
- Roles: CUSTOMER, RESTAURANT_OWNER, ADMIN

### API Security
- User registration and login are public endpoints
- All other endpoints require valid JWT token
- Role-based access control implemented

## Development Guidelines

### Adding New Features
1. Follow the existing package structure
2. Implement proper validation and error handling
3. Add comprehensive unit tests
4. Update API documentation
5. Test inter-service communication

### Best Practices
- Use DTOs for API contracts
- Implement proper exception handling
- Follow RESTful API conventions
- Use appropriate HTTP status codes
- Implement proper logging

## Troubleshooting

### Common Issues

1. **Service Discovery Issues**
   - Ensure Eureka server is running first
   - Check network connectivity between services
   - Verify Eureka client configuration

2. **Database Connection Issues**
   - Check H2 console for development
   - Verify MySQL configuration for production
   - Check database permissions

3. **Inter-Service Communication Issues**
   - Verify Feign client configuration
   - Check service registration in Eureka
   - Review network connectivity

4. **Authentication Issues**
   - Verify JWT token is valid and not expired
   - Check JWT secret configuration
   - Ensure proper Authorization header format

## Contributing

1. Fork the repository
2. Create a feature branch
3. Implement your changes with tests
4. Submit a pull request with detailed description

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Contact

For questions or support, please contact the development team.
