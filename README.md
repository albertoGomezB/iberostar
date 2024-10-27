# iberostar
This repository is for the technical test in Iberostar 

## Description

This project is a RESTful API developed with **Spring Boot**. It provides full CRUD (Create, Read, Update, Delete) functionality, along with advanced features such as pagination, custom search options, centralized exception handling, and caching.

The objective of this project is to demonstrate a clean, scalable backend architecture for database management, following best practices and solid design principles.

---

## Features

- **CRUD Operations**: Enables creation, retrieval, updating, and deletion of entities in the database.
- **Pagination**: Supports paginated results for optimized data retrieval.
- **Search Functionality**: Allows searching for specific records by parameters.
- **Centralized Exception Handling**: Provides uniform error responses throughout the API.
- **Aspect-Oriented Logging**: Logs requests under specific conditions, ensuring traceability.
- **Caching**: Implements caching for frequently accessed data to enhance response times and reduce load.

---

## Technologies Used

- **Java 21+**: Latest version for enhanced performance and compatibility.
- **Spring Boot 3**: Foundation for RESTful API development and simplified configuration.
- **Spring Data JPA**: Facilitates seamless integration with databases and ORM.
- **H2 Database**: In-memory database solution for development and testing.
- **Maven**: For project building and dependency management.
- **JUnit and Mockito**: Unit and integration testing to ensure code reliability.
- **SLF4J with Logback**: Structured logging to enhance debugging and monitoring.
- **OpenAPI**: Comprehensive API documentation for ease of use.
- **SOLID Principles**: Applied to ensure code maintainability and extensibility.

---

## Installation

### Prerequisites

- **Java 21+**: Ensure the latest Java version is installed. Verify installation with:
  ```bash
  java -version

  ## API Endpoints

### Ships

| Method | Endpoint                  | Description                                      |
|--------|----------------------------|--------------------------------------------------|
| GET    | `/ships`                  | Retrieve a paginated list of ships.              |
| GET    | `/ships/{id}`             | Retrieve a single ship by ID.                    |
| GET    | `/ships/search?name={name}` | Search for ships containing `{name}` in their name. |
| POST   | `/ships`                  | Create a new ship.                               |
| PUT    | `/ships/{id}`             | Update an existing ship by ID.                   |
| DELETE | `/ships/{id}`             | Delete a ship by ID.                             |

### Series

| Method | Endpoint                  | Description                                      |
|--------|----------------------------|--------------------------------------------------|
| GET    | `/series`                 | Retrieve a list of series.             |
| GET    | `/series/{id}`            | Retrieve a single series by ID.                  |
| GET    | `/series/search?name={name}` | Search for series containing `{name}` in their title. |
| POST   | `/series`                 | Create a new series entry.                       |
| PUT    | `/series/{id}`            | Update an existing series by ID.                 |
| DELETE | `/series/{id}`            | Delete a series by ID.                           |

### Movies

| Method | Endpoint                  | Description                                      |
|--------|----------------------------|--------------------------------------------------|
| GET    | `/movies`                 | Retrieve a list of movies.             |
| GET    | `/movies/{id}`            | Retrieve a single movie by ID.                   |
| GET    | `/movies/search?name={name}` | Search for movies containing `{name}` in the title. |
| POST   | `/movies`                 | Create a new movie entry.                        |
| PUT    | `/movies/{id}`            | Update an existing movie by ID.                  |
| DELETE | `/movies/{id}`            | Delete a movie by ID.                            |



## Author : Alberto Gómez Barral

