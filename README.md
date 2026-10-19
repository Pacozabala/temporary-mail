# temporary-mail
A temporary email backend built with Java and Spring Boot. The project provides REST API endpoints for creating temporary mailboxes, retireving mailbox info, and managing received emails.

This project was developed to practice REST API design, Spring Boot app achritecture, relational database modeling, and data persistence using PotsgreSQL.

**Project status:** Work in progress. Core mailbox and email management features are under development. SMTP integration and actual external email reception have not yet been implemented.

## Features
### Implemented
- Create a temporary mailbox with a generated random email address.
- Set mailbox creation and expiration timestamps.
- Retrieve mailbox information.
- Delete mailboxes.
- Associate emails with respective mailboxes using entity relationships.
- Retrieve emails belonging to a mailbox.
- Retrieve individual emails (includes mailbox ownership validation).
- Paginate and sort inbox results, including newest-first ordering.
- Track email read/unread status.
- Filter inbox results by read status.
- Use DTOs to separate API responses from persistence entities.
- Persist application data using PostgreSQL.
- Handle missing resources and invalid requests using custom exceptions and validation.

## Technology Stack

- **Language:** Java
- **Framework:** Spring Boot
- **Build Tool:** Maven
- **Database:** PostgreSQL
- **Persistence:** Spring Data JPA, Hibernate
- **API:** REST, Spring Web
- **Validation:** Jakarta Bean Validation
- **Testing:** JUnit, Spring Boot Test

## Project Structure

```text
src/
└── main/
    ├── java/com/pacozabala/tempmail/
    │   ├── config/
    │   ├── controller/
    │   ├── dto/
    │   ├── exception/
    │   ├── model/
    │   ├── repository/
    │   └── service/
    └── resources/
        └── application.properties

└── test/
    └── java/com/pacozabala/tempmail/
```

The application follows a layered architecture:
- **Controller:** handles HTTP requests and responses.
- **Service:** contains business logic and application rules.
- **Repository:** handles databse operations through Spring Data JPA.
- **Model:** defines persistent entities and their relationships.
- **DTO:** defines data accepted and returned by API endpoints.
- **Exception:** contains custom exceptions and centralized error handling.
- **Config:** currently empty, intended to contain app configuraton.

## Getting Started
### Prerequisites
- Java version compatible with project configuration.
- PostgreSQL
- Maven, or you may use the maven wrapper included in the repo.
- Git (for cloning)

### 1. Clone repository
```bash
git clone https://github.com/Pacozabala/temporary-mail
cd temporary-mail/backend
```

### 2. Create the database
Open PostgreSQL and create the project database:
```sql
CREATE DATABASE tempmail;
```

### 3. Configure environment variables
Configure the following environment variables for your local PostgreSQL instance:
| Variable | Description |
|---|---|
| DB_URL | PostgreSQL JDBC connection URL |
| DB_USERNAME | PostgreSQL username |
| DB_PASSWORD | PostgreSQL password |

Example PowerShell config:
```bash
$env:DB_URL="jdbc:postgresql://localhost:5432/tempmail"
$env:DB_USERNAME="your_username"
$env:DB_PASSWORD="your_password"
```

Use your own local database credentials. Do not commit passwords or other secrets to version control. 

Ensure that application.properties references these variables and that the PostgreSQL driver is included in the project dependencies.

### 4. Run the application
From the backend directory, run:
```bash
.\mvnw.cmd spring-boot:run
```

Alternatively, if Maven is installed:
```bash
mvn spring-boot:run
```
The application will start on the configured port, typically 8080.

## API Endpoints
Base URL: http://localhost:8080
| Method | Endpoint | Description |
|---|---|---|
| POST | /mailboxes | Create a temporary mailbox |
| GET | /mailboxes/{id} | Retrieve mailbox information |
| DELETE | /mailboxes/{id} | Delete a mailbox |
| GET | /mailboxes/{id}/emails | Retrieve a mailbox's emails |
| GET | /mailboxes/{id}/emails/{emailId} | Retrieve an individual email |

### Inbox Pagination and Sorting
The inbox endpoint supports pagination and sorting through Spring Data's `Pageable` mechanism.
```
GET /mailboxes/1/emails?page=0&size=20&sort=receivedAt,desc
```

This requests the first page of up to 20 emails, ordered by receipt time from newest to oldest.
Read-status filtering can be used alongside pagination.

## Database Design
### Mailbox
Represents a temporary mailbox.
- id
- address
- createdAt
- expiresAt

### Email
Represents an email message received by a mailbox.
- id
- mailbox
- sender
- recipient
- subject
- body
- receivedAt
- isRead

Each email belongs to one mailbox through a JPA `@ManyToOne` relationship. A mailbox can have multiple emails through a corresponding `@OneToMany` relationship, if configured on the mailbox entity.

## Testing
The project includes tests for application behavior and API functionality using JUnit and Spring Boot testing tools.

Tests should cover:
- Mailbox creation, retrieval, and deletion.
- Email retrieval and mailbox ownership checks.
- Pagination and sorting.
- Read-status filtering and updates, where implemented.
- Missing-resource and invalid-request handling.
- Persistence of mailbox and email entities.

Tests that use PostgreSQL should be run against an appropriately configured test database. Avoid running cleanup operations against a database containing data you want to preserve.

## Current Limitations

- The application does not yet receive real emails from external senders.
- SMTP server integration and inbound email processing have not been implemented.
- Domain registration and DNS/MX configuration have not been completed.
- Automatic mailbox expiration and expired-data cleanup may require further implementation.
- A frontend interface has not yet been integrated.

At this stage, the project focuses on the backend API and database layer rather than a complete, publicly accessible temporary email service.

## Future Improvements

- Integrate an SMTP receiving server such as Postfix.
- Configure DNS and MX records for a dedicated email domain.
- Parse incoming email headers and MIME content.
- Store received messages in PostgreSQL and associate them with the appropriate mailbox.
- Implement automatic expiration and cleanup of temporary mailboxes and emails.
- Add more comprehensive integration and persistence tests.
- Develop a React frontend for mailbox creation and inbox viewing.
- Add real-time inbox updates, potentially using Server-Sent Events.
- Containerize the application with Docker and prepare it for deployment.

Learning Objectives

This project explores:
- RESTful API design and HTTP methods.
- Layered application architecture in Spring Boot.
- JPA entity relationships and relational database design.
- DTOs, validation, and exception handling.
- Pagination, sorting, and filtering.
- Automated testing and database persistence.
- The infrastructure involved in receiving and processing email.