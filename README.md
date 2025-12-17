# Bank Cards Service

This service manages bank cards: viewing, blocking, transfers between cards, and user administration.

## Technology Stack

- Java 17 / Spring Boot 3
- PostgreSQL
- Liquibase
- Redis
- JWT for authentication
- Docker / Docker Compose
- Swagger UI / OpenAPI
### Important information
The project has test data on the path `src/main/resources/db/migration/sql/seed`.
> If you don't need them, then delete the migration files.

## Setup and Running

### 1. Clone the Repository

```bash
git clone https://github.com/vavilonpal/Bank-Card-Managment-System

cd <clone-root-path>
```

### 2.  Run with Docker

Enter the following command in the root of the project
```bash
docker-compose up --build -d

```

### 3. Connection to running instance
Open postman or Browser and connect to 
```http request
http://localhost:8080/
```
For get Swagger OpenAPI definition connect to:
```http request
http://localhost:8080/swagger-ui/index.html
```
