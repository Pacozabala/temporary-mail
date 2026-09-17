# Temp Mail Project Roadmap

A disposable email service built for learning backend development, email infrastructure, real-time communication, and deployment.

## Project Goal

Build a web application that allows users to:

* Generate a temporary email address
* Receive emails sent to that address
* View received emails through a web interface
* Automatically expire temporary mailboxes
* Automatically delete expired emails
* Receive new emails in real time
* Handle basic security and abuse prevention

### Target Architecture

```text
                        Internet
                           │
                           │ SMTP
                           ▼
                    ┌──────────────┐
                    │    Postfix   │
                    │ SMTP Server  │
                    └──────┬───────┘
                           │
                           ▼
┌──────────────┐    ┌──────────────┐
│    React     │◄──►│ Spring Boot  │
│  Frontend    │    │    Backend   │
└──────────────┘    └──────┬───────┘
                           │
                  ┌────────┴────────┐
                  ▼                 ▼
           ┌──────────────┐  ┌──────────────┐
           │  PostgreSQL  │  │    Redis     │
           └──────────────┘  └──────────────┘
```

---

# Phase 0 – Project Setup

## Learn

* Git and GitHub repository structure
* Spring Initializr
* Maven
* Spring Boot project structure
* Environment variables
* `.gitignore`

## Tasks

* [/] Create GitHub repository
* [/] Clone repository locally
* [/] Create `backend/` directory
* [/] Generate Spring Boot project with Spring Initializr
* [/] Configure Maven
* [/] Configure Java
* [/] Add Spring Web
* [/] Add Spring Data JPA
* [/] Add Validation
* [/] Add PostgreSQL Driver
* [/] Add Spring Boot DevTools
* [/] Verify application starts
* [/] Configure `.gitignore`
* [/] Create initial README
* [/] Make initial Git commit

## Initial Stack

```text
Java
Spring Boot
Maven
PostgreSQL
Git/GitHub
```

### Milestone

> Spring Boot application runs successfully from the repository.

---

# Phase 1 – Project Architecture

## Learn

* Controller
* Service
* Repository
* Entity/model
* DTO
* Dependency injection
* Separation of concerns

## Tasks

* [/] Create package structure
* [/] Create `controller`
* [/] Create `service`
* [/] Create `repository`
* [/] Create `model`
* [/] Create `dto`
* [/] Create `exception`
* [/] Create `config`
* [/] Create initial controller
* [/] Create initial service
* [/] Verify dependency injection

### Target Structure

```text
com.pacozabala.tempmail
├── controller
├── service
├── repository
├── model
├── dto
├── exception
└── config
```

### Milestone

> Understand the application's request flow from Controller → Service → Repository.

---

# Phase 2 – Database & Domain Model

## Learn

* JPA entities
* Relationships
* Primary keys
* Foreign keys
* PostgreSQL
* Hibernate
* Database schema design

## Core Entities

### Mailbox

Possible fields:

```text
id
address
createdAt
expiresAt
```

### Email

Possible fields:

```text
id
mailboxId
sender
recipient
subject
body
receivedAt
```

### Attachment

Potential future entity:

```text
id
emailId
filename
contentType
size
storagePath
```

## Tasks

* [/] Create PostgreSQL database
* [/] Configure database connection
* [/] Create `Mailbox` entity
* [/] Create `Email` entity
* [/] Define mailbox/email relationship
* [/] Create repositories
* [/] Verify Hibernate creates tables
* [/] Test basic persistence
* [ ] Decide how expired data will be identified

### Milestone

> Mailboxes and emails can be persisted in PostgreSQL.

---

# Phase 3 – Temporary Mailbox API

## Learn

* REST API design
* HTTP methods
* DTOs
* Request/response handling
* UUIDs/random identifiers
* Validation

## Endpoints

### Create mailbox

```http
POST /mailboxes
```

Response:

```json
{
  "id": "...",
  "address": "abc123@example.com",
  "expiresAt": "..."
}
```

### Get mailbox

```http
GET /mailboxes/{id}
```

### Delete mailbox

```http
DELETE /mailboxes/{id}
```

### Get inbox

```http
GET /mailboxes/{id}/emails
```

### Get email

```http
GET /mailboxes/{id}/emails/{emailId}
```

## Tasks

* [ ] Create mailbox DTOs
* [ ] Create mailbox service
* [ ] Create mailbox controller
* [ ] Generate unique mailbox addresses
* [ ] Set mailbox expiration
* [ ] Implement mailbox retrieval
* [ ] Implement mailbox deletion
* [ ] Implement inbox endpoint
* [ ] Implement individual email endpoint
* [ ] Add validation
* [ ] Add exception handling
* [ ] Test endpoints with Postman

### Milestone

> The backend can create temporary mailboxes and expose their inbox through a REST API.

---

# Phase 4 – Email Storage

## Learn

* Email data modeling
* Entity relationships
* Database queries
* Pagination
* Sorting
* Filtering

## Tasks

* [ ] Create email repository methods
* [ ] Create email DTOs
* [ ] Add email service
* [ ] Return emails belonging to a mailbox
* [ ] Sort newest emails first
* [ ] Add pagination
* [ ] Add email read/unread status
* [ ] Add endpoint for marking emails as read
* [ ] Add email deletion
* [ ] Test email persistence

### Milestone

> The application can store and retrieve email messages associated with temporary mailboxes.

---

# Phase 5 – SMTP & Mail Receiving

This is the major infrastructure phase.

## Learn

* SMTP
* MX records
* DNS
* Mail servers
* SMTP delivery
* Email headers
* MIME
* Mail parsing

## Architecture

```text
Sender
  │
  │ SMTP
  ▼
MX Record
  │
  ▼
Postfix
  │
  ▼
Mail Processing
  │
  ▼
Spring Boot
  │
  ▼
PostgreSQL
```

## Tasks

* [ ] Understand SMTP
* [ ] Understand MX records
* [ ] Purchase/configure a domain
* [ ] Configure DNS
* [ ] Configure MX record
* [ ] Install/configure Postfix
* [ ] Configure mail reception
* [ ] Accept mail for the temporary-mail domain
* [ ] Forward received messages to the application
* [ ] Parse incoming email
* [ ] Extract sender
* [ ] Extract recipient
* [ ] Extract subject
* [ ] Extract body
* [ ] Store parsed email in PostgreSQL
* [ ] Test with external email providers

### Milestone

> Someone can send an email to a generated temporary address and the application stores it automatically.

---

# Phase 6 – Email Parsing & MIME

## Learn

* MIME
* Plain-text email
* HTML email
* Multipart messages
* Email headers
* Attachments

## Tasks

* [ ] Parse plain-text emails
* [ ] Parse HTML emails
* [ ] Handle multipart messages
* [ ] Extract HTML body
* [ ] Extract text body
* [ ] Identify attachments
* [ ] Store attachment metadata
* [ ] Decide where attachment files will be stored
* [ ] Handle malformed emails safely
* [ ] Add limits on email size
* [ ] Add limits on attachment size

### Milestone

> The service can correctly process common real-world email formats.

---

# Phase 7 – Mailbox Expiration & Background Jobs

## Learn

* Scheduled tasks
* Background processing
* Data cleanup
* Time-based logic
* `@Scheduled`

## Tasks

* [ ] Add expiration timestamp to mailboxes
* [ ] Create scheduled cleanup task
* [ ] Find expired mailboxes
* [ ] Delete expired emails
* [ ] Delete expired attachments
* [ ] Delete expired mailboxes
* [ ] Configure cleanup interval
* [ ] Test expiration behavior
* [ ] Prevent access to expired mailboxes

### Example

```text
Mailbox created
      │
      ▼
   10-minute
   lifetime
      │
      ▼
   expiresAt
      │
      ▼
Scheduled cleanup
      │
      ▼
Delete mailbox
      │
      ▼
Delete associated data
```

### Milestone

> Temporary mailboxes automatically disappear after their lifetime expires.

---

# Phase 8 – React Frontend

## Learn

* React
* TypeScript
* Components
* State management
* API requests
* Forms
* Responsive UI

## Frontend Structure

```text
frontend/
├── src/
│   ├── components/
│   ├── pages/
│   ├── services/
│   ├── types/
│   └── App.tsx
└── package.json
```

## Tasks

* [ ] Create React + TypeScript project
* [ ] Create application layout
* [ ] Create temporary mailbox page
* [ ] Generate mailbox
* [ ] Display mailbox address
* [ ] Add copy-to-clipboard button
* [ ] Display expiration countdown
* [ ] Create inbox component
* [ ] Create email list
* [ ] Create email viewer
* [ ] Add loading states
* [ ] Add error states
* [ ] Add responsive styling

### Milestone

> Users can create and use a temporary mailbox entirely through the web interface.

---

# Phase 9 – Real-Time Inbox

## Learn

* WebSockets or Server-Sent Events
* Event-driven architecture
* Real-time frontend updates
* Connection management

## Recommended Approach

Start with **Server-Sent Events (SSE)** because the communication pattern is primarily:

```text
Backend ──────────► Browser
```

rather than two-way communication.

## Flow

```text
Email arrives
     │
     ▼
Spring Boot
     │
     ├── Store email
     │
     ▼
SSE Event
     │
     ▼
React
     │
     ▼
Inbox updates
```

## Tasks

* [ ] Learn SSE
* [ ] Create SSE endpoint
* [ ] Track mailbox connections
* [ ] Publish new-email events
* [ ] Subscribe from React
* [ ] Update inbox automatically
* [ ] Handle disconnected clients
* [ ] Reconnect automatically
* [ ] Test multiple simultaneous mailboxes

### Milestone

> New emails appear in the inbox without refreshing the page.

---

# Phase 10 – Redis & Temporary State

## Learn

* Redis
* Caching
* TTL
* Distributed state
* Cache invalidation

## Potential Uses

* Mailbox lookup
* Rate limiting
* Temporary session/state data
* Active SSE connections
* Expiration tracking

## Tasks

* [ ] Add Redis
* [ ] Configure Spring Data Redis
* [ ] Cache mailbox information
* [ ] Add TTL where appropriate
* [ ] Evaluate PostgreSQL vs Redis responsibilities
* [ ] Test Redis failure behavior

### Milestone

> Redis is used where temporary/high-speed state provides a meaningful benefit.

---

# Phase 11 – Security & Abuse Prevention

A public disposable-email service needs basic protection.

## Learn

* Rate limiting
* Input validation
* HTML sanitization
* CORS
* Security headers
* Abuse prevention

## Tasks

* [ ] Add request rate limiting
* [ ] Limit mailbox creation
* [ ] Limit email size
* [ ] Limit attachment size
* [ ] Sanitize HTML email content
* [ ] Prevent dangerous script execution
* [ ] Configure CORS
* [ ] Add security headers
* [ ] Validate email addresses
* [ ] Prevent mailbox enumeration where appropriate
* [ ] Consider CAPTCHA for public deployment
* [ ] Add basic abuse monitoring

### Important

Email bodies are **untrusted content**.

Never blindly inject received HTML into the frontend.

```text
Incoming email
      │
      ▼
Untrusted HTML
      │
      ▼
Sanitization
      │
      ▼
Safe rendering
```

### Milestone

> The service has reasonable protections against common abuse and malicious email content.

---

# Phase 12 – Testing

## Learn

* Unit testing
* Integration testing
* MockMvc
* Repository testing
* Testcontainers

## Backend Tests

### Mailbox

* [ ] Create mailbox
* [ ] Get mailbox
* [ ] Delete mailbox
* [ ] Expired mailbox
* [ ] Duplicate address handling

### Email

* [ ] Store email
* [ ] Retrieve emails
* [ ] Retrieve individual email
* [ ] Delete email
* [ ] Mark email as read

### Email processing

* [ ] Parse plain-text email
* [ ] Parse HTML email
* [ ] Parse multipart email
* [ ] Handle malformed email
* [ ] Handle oversized email

### Cleanup

* [ ] Expired mailbox cleanup
* [ ] Associated email cleanup
* [ ] Attachment cleanup

## Infrastructure Testing

* [ ] Test PostgreSQL with Testcontainers
* [ ] Test Redis with Testcontainers
* [ ] Test mail-processing pipeline
* [ ] Test API integration

### Milestone

> Important application behavior is covered by automated tests.

---

# Phase 13 – Dockerization

## Learn

* Docker
* Docker Compose
* Containers
* Container networking
* Environment configuration

## Services

Eventually:

```text
docker-compose
│
├── backend
├── frontend
├── postgres
├── redis
└── mail server
```

## Tasks

* [ ] Create backend Dockerfile
* [ ] Create frontend Dockerfile
* [ ] Create PostgreSQL service
* [ ] Create Redis service
* [ ] Configure container networking
* [ ] Configure environment variables
* [ ] Create Docker Compose configuration
* [ ] Run complete application locally
* [ ] Test fresh installation

### Milestone

> The complete application can be started with Docker Compose.

---

# Phase 14 – Production Deployment

## Learn

* Linux server administration
* DNS
* HTTPS
* Reverse proxies
* Deployment
* Environment variables
* Logging
* Monitoring

## Components

```text
                         Internet
                            │
                            ▼
                         Nginx
                       /        \
                      /          \
                     ▼            ▼
                Frontend      Spring Boot
                                  │
                        ┌─────────┼─────────┐
                        ▼         ▼         ▼
                    PostgreSQL  Redis     Postfix
```

## Tasks

* [ ] Choose hosting provider
* [ ] Set up Linux server
* [ ] Install Docker
* [ ] Configure domain
* [ ] Configure DNS
* [ ] Configure MX records
* [ ] Configure Nginx
* [ ] Configure HTTPS
* [ ] Deploy application
* [ ] Configure production environment variables
* [ ] Configure database persistence
* [ ] Configure mail server
* [ ] Test receiving external email
* [ ] Configure logging
* [ ] Configure backups where appropriate
* [ ] Monitor application health

### Milestone

> The application is publicly accessible and can receive real email.

---

# Phase 15 – CI/CD

## Learn

* GitHub Actions
* Automated testing
* Build pipelines
* Deployment automation

## Tasks

* [ ] Create GitHub Actions workflow
* [ ] Run tests on pull requests
* [ ] Build backend automatically
* [ ] Build frontend automatically
* [ ] Build Docker images
* [ ] Deploy after successful build
* [ ] Add status badges to README

### Milestone

> Changes can be tested and deployed automatically.

---

# Phase 16 – Documentation

## Documentation

```text
docs/
├── architecture.md
├── api.md
├── database.md
├── email.md
├── deployment.md
└── testing.md
```

## Tasks

* [ ] Document project architecture
* [ ] Document API endpoints
* [ ] Document database schema
* [ ] Document SMTP architecture
* [ ] Document email processing
* [ ] Document expiration system
* [ ] Document security considerations
* [ ] Document local setup
* [ ] Document Docker setup
* [ ] Document deployment
* [ ] Document testing
* [ ] Add architecture diagram
* [ ] Add screenshots
* [ ] Update README

### Milestone

> Another developer can clone the repository and understand how the system works.

---

# Phase 17 – Portfolio Polish

## Tasks

* [ ] Review project structure
* [ ] Remove unused code
* [ ] Remove secrets
* [ ] Review `.gitignore`
* [ ] Improve README
* [ ] Add project screenshots
* [ ] Add architecture diagram
* [ ] Add API documentation
* [ ] Add setup instructions
* [ ] Add deployment URL
* [ ] Add demo instructions
* [ ] Add technologies section
* [ ] Add key technical challenges
* [ ] Add lessons learned
* [ ] Clean Git history where appropriate
* [ ] Create release/tag

## README Should Highlight

* What the project does
* Architecture
* Tech stack
* How email delivery works
* How temporary mailboxes work
* Security considerations
* API examples
* Screenshots
* Local setup
* Docker setup
* Deployment
* Testing

### Final Milestone

> Portfolio-ready project demonstrating backend development, email infrastructure, databases, real-time communication, security, testing, Docker, and deployment.

---

# Final Tech Stack

## Backend

```text
Java
Spring Boot
Spring Web
Spring Data JPA
Hibernate
Maven
```

## Database

```text
PostgreSQL
Redis
```

## Email Infrastructure

```text
SMTP
Postfix
MIME
DNS / MX Records
```

## Frontend

```text
React
TypeScript
HTML
CSS
```

## Real-Time Communication

```text
Server-Sent Events (SSE)
```

## Testing

```text
JUnit
Mockito
Spring Boot Test
MockMvc
Testcontainers
```

## DevOps

```text
Docker
Docker Compose
Nginx
GitHub Actions
Linux
```

## Development Tools

```text
Git
GitHub
Postman
VS Code / IntelliJ IDEA
```

---

# Suggested Repository Structure

```text
temp-mail/
│
├── backend/
│   ├── pom.xml
│   └── src/
│
├── frontend/
│   ├── package.json
│   └── src/
│
├── docs/
│   ├── architecture.md
│   ├── api.md
│   ├── database.md
│   ├── email.md
│   ├── deployment.md
│   └── testing.md
│
├── docker-compose.yml
├── .gitignore
└── README.md
```

---

# Overall Learning Progression

```text
Spring Boot
     │
     ▼
REST API
     │
     ▼
PostgreSQL + JPA
     │
     ▼
Temporary Mailboxes
     │
     ▼
SMTP / Postfix
     │
     ▼
Email Parsing
     │
     ▼
Expiration / Background Jobs
     │
     ▼
React + TypeScript
     │
     ▼
SSE / Real-Time Updates
     │
     ▼
Redis
     │
     ▼
Security / Abuse Prevention
     │
     ▼
Testing
     │
     ▼
Docker
     │
     ▼
Deployment
     │
     ▼
CI/CD
     │
     ▼
Portfolio Project
```

# Definition of Done

The project is considered complete when a user can:

1. Open the website.
2. Generate a temporary email address.
3. Copy the address.
4. Give the address to an external service.
5. Receive an email at that address.
6. See the email appear automatically.
7. Open and read the email safely.
8. Wait for the mailbox to expire.
9. Have the mailbox and its data automatically removed.

The backend should have automated tests, the application should be Dockerized, the production deployment should use HTTPS, and the repository should contain clear documentation explaining the architecture and email-processing pipeline.
