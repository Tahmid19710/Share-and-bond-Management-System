# Share and Bond Management System

A Spring Boot based **Shareholder Management System** designed around a structured **Maker–Checker–Approver** workflow.

The application allows shareholder information to be created, reviewed, approved, returned for modification, and finally activated through a controlled multi-stage approval process.

---

## Project Overview

The **Share and Bond Management System** is a web-based application developed using Spring Boot.

The system follows a layered architecture:

```text
Browser
   ↓
Thymeleaf UI
   ↓
Controller Layer
   ↓
Service Layer
   ↓
Repository Layer
   ↓
Entity Layer
   ↓
Database
```

Each layer has a separate responsibility:

- **Thymeleaf UI** handles the user interface.
- **Controller Layer** receives HTTP requests.
- **Service Layer** contains application business logic.
- **Repository Layer** communicates with the database.
- **Entity Layer** maps Java objects to database tables.
- **Database** stores shareholder and approval workflow information.

---

# Main Features

## Authentication

The application provides role-based authentication using **Spring Security**.

Current roles:

```text
MAKER
CHECKER
APPROVER
```

After successful authentication, users are redirected to their respective dashboards.

```text
Maker
   ↓
/dashboard

Checker
   ↓
/checker/dashboard

Approver
   ↓
/approver/dashboard
```

---

# Role-Based Authorization

Different parts of the application are protected according to user roles.

Example access rules:

```text
/dashboard
    → MAKER

/checker/**
    → CHECKER

/approver/**
    → APPROVER
```

This prevents users from accessing modules that do not belong to their assigned role.

---

# Maker–Checker–Approver Workflow

The core of the system follows the following workflow:

```text
Maker
   ↓
Create Shareholder
   ↓
Pending Checker
   ↓
Checker Review
   ↓
Pending Approver
   ↓
Approver Review
   ↓
Approved
   ↓
Active Shareholder
```

---

## Maker Workflow

The Maker creates new shareholder information.

Flow:

```text
Maker
   ↓
Create Shareholder Form
   ↓
AccountShareController
   ↓
AccountShareService
   ↓
AccountShareRepository
   ↓
T_ACCOUNT_SHARE
```

When a new shareholder is created:

```text
T_ACCOUNT_SHARE.STATUS = 0
```

An approval request is also created:

```text
STATUS = PENDING_CHECKER
CURRENT_STAGE = CHECKER
```

This means that a newly created shareholder is not immediately active.

The record must pass through the approval workflow.

---

# Checker Workflow

The Checker reviews records submitted by the Maker.

## Checker Approves

Before approval:

```text
STATUS = PENDING_CHECKER
CURRENT_STAGE = CHECKER
```

After approval:

```text
STATUS = PENDING_APPROVER
CURRENT_STAGE = APPROVER
```

An approval history record is also created:

```text
ACTION = APPROVED
STAGE = CHECKER
```

The request then moves to the Approver.

---

## Checker Rejects / Returns

The Checker can return a request to the Maker for modification.

The status becomes:

```text
STATUS = RETURNED_FOR_MODIFICATION
CURRENT_STAGE = MAKER
```

The rejection/return action is also stored in approval history.

The Maker can then modify the required information and continue the workflow.

---

# Approver Workflow

The Approver performs the final review.

Before final approval:

```text
APPROVAL_REQUEST.STATUS = PENDING_APPROVER
```

After approval:

```text
APPROVAL_REQUEST.STATUS = APPROVED
APPROVAL_REQUEST.CURRENT_STAGE = COMPLETED
```

The shareholder record is then activated:

```text
T_ACCOUNT_SHARE.STATUS = 1
```

Therefore:

```text
STATUS = 0
```

means the shareholder is not yet fully approved.

```text
STATUS = 1
```

means the shareholder has completed the approval process and is active.

---

# Technology Stack

## Backend

- Java
- Spring Boot
- Spring MVC
- Spring Security
- Spring Data JPA
- Hibernate

## Frontend

- HTML
- CSS
- Thymeleaf

## Database

- Relational Database
- JPA/Hibernate based database access

## Development Tools

- IntelliJ IDEA
- Git
- GitHub

---

# Project Architecture

The project uses a layered Spring Boot architecture.

```text
src/
└── main/
    ├── java/
    │   └── ...
    │       ├── config/
    │       ├── controller/
    │       ├── service/
    │       ├── repository/
    │       └── entity/
    │
    └── resources/
        ├── templates/
        ├── static/
        └── application.properties
```

---

# Package Responsibilities

## `config`

Contains application configuration.

Important class:

```text
SecurityConfig.java
```

Responsibilities:

- Authentication configuration
- Authorization configuration
- Role management
- Login configuration
- URL access control
- Redirect handling

---

# `controller`

Controllers receive HTTP requests from the frontend.

Important controllers include:

```text
AuthController
AccountShareController
CheckerController
ApproverController
```

---

## AuthController

Responsible for authentication-related page handling and application login flow.

---

## AccountShareController

Responsible for shareholder management.

Main operations:

```text
Create
Read
Update
Delete
List
```

Important endpoints include:

```text
GET /shareholders
```

Displays the shareholder list.

```text
GET /shareholders/create
```

Opens the shareholder creation form.

```text
POST /shareholders/save
```

Creates a shareholder and starts the approval workflow.

```text
GET /shareholders/edit/{oid}
```

Loads an existing shareholder for editing.

```text
POST /shareholders/update
```

Updates shareholder information.

```text
GET /shareholders/delete/{oid}
```

Handles shareholder deletion.

---

# CheckerController

`CheckerController` manages the Checker stage of the approval workflow.

Pending shareholder requests can be retrieved from:

```text
/checker/shareholders
```

Typical process:

```text
Checker Dashboard
       ↓
CheckerController
       ↓
ApprovalRequestRepository
       ↓
findByStatus(PENDING_CHECKER)
       ↓
Database
       ↓
Pending Requests
```

### Approve Process

```text
1. Find ApprovalRequest
2. Validate current status
3. Set CURRENT_STAGE = APPROVER
4. Set STATUS = PENDING_APPROVER
5. Save ApprovalAction history
```

### Return Process

```text
1. Find ApprovalRequest
2. Set CURRENT_STAGE = MAKER
3. Set STATUS = RETURNED_FOR_MODIFICATION
4. Save action history
```

---

# ApproverController

`ApproverController` performs the final approval operation.

Typical process:

```text
APPROVAL_REQUEST
       ↓
Approver Review
       ↓
Final Approval
       ↓
Activate Shareholder
```

Database changes include:

```text
APPROVAL_REQUEST
PENDING_APPROVER → APPROVED
```

and:

```text
T_ACCOUNT_SHARE
STATUS 0 → STATUS 1
```

A final approval history record is also stored.

---

# Service Layer

The Service layer contains the application business logic.

General request flow:

```text
Controller
   ↓
Service
   ↓
Repository
```

Responsibilities may include:

- Input validation
- Business rule processing
- Entity creation
- Status management
- Workflow management
- Repository communication

Example:

```text
Controller receives form data
        ↓
Service validates data
        ↓
Service creates Entity
        ↓
Service applies business rules
        ↓
Repository saves data
```

---

# Repository Layer

Repositories communicate with the database using **Spring Data JPA**.

Example:

```java
public interface AccountShareRepository
        extends JpaRepository<AccountShare, Long> {
}
```

`JpaRepository` provides commonly required database operations such as:

```text
save()
findById()
findAll()
delete()
```

Spring Data JPA automatically generates much of the SQL required for these operations.

---

# Entity Layer

Entities represent database tables as Java classes.

Example:

```java
@Entity
@Table(name = "T_ACCOUNT_SHARE")
public class AccountShare {

    @Id
    private Long oid;

    private String custName;
    private String phone;
}
```

Mapping:

```text
AccountShare.java
       ↓
T_ACCOUNT_SHARE
```

Entity fields are mapped to corresponding database columns.

---

# Main Database Entities

The core system contains the following logical entities:

```text
AccountShare
ApprovalRequest
ApprovalAction
```

---

# AccountShare Mapping

Important fields include:

```text
oid       → OID
folioBo   → FOLIO_BO
custName  → CUST_NAME
phone     → PHONE
email     → EMAIL
status    → STATUS
```

---

# ApprovalRequest Mapping

Important fields include:

```text
requestId     → REQUEST_ID
entityId      → ENTITY_ID
status        → STATUS
currentStage  → CURRENT_STAGE
```

---

# ApprovalAction Mapping

Important fields include:

```text
actionId   → ACTION_ID
requestId  → REQUEST_ID
action     → ACTION
stage      → STAGE
actorId    → ACTOR_ID
```

---

# Database Relationship

The approval workflow is related to the shareholder record.

Conceptually:

```text
T_ACCOUNT_SHARE
       |
       |
       *
APPROVAL_REQUEST
       |
       |
       *
APPROVAL_ACTION
```

`APPROVAL_REQUEST.ENTITY_ID` references the shareholder's `OID`.

One shareholder can therefore be associated with workflow information and approval history.

---

# OID Generation

Shareholder OIDs are currently generated using:

```java
System.currentTimeMillis()
```

Example:

```text
1758339278123
```

The field uses `Long` because timestamp-based values exceed the normal Integer range.

---

# Complete Request Lifecycle

A normal Spring MVC request follows this sequence:

```text
User
 ↓
Thymeleaf Page
 ↓
HTTP Request
 ↓
Controller
 ↓
Service
 ↓
Repository
 ↓
JPA / Hibernate
 ↓
Database
 ↓
Repository
 ↓
Service
 ↓
Controller
 ↓
Thymeleaf Response
 ↓
User
```

Detailed steps:

```text
1. User performs an action from a Thymeleaf page.

2. Spring MVC maps the request to a Controller method.

3. Controller receives the submitted information.

4. Controller calls the Service layer.

5. Service applies business rules.

6. Service prepares or updates Entity objects.

7. Repository communicates with the database using JPA/Hibernate.

8. Database operation completes.

9. Result is returned to the Controller.

10. Controller returns the required Thymeleaf view.
```

---

# Example: Create Shareholder

```text
HTML Form
   ↓
AccountShareController
   ↓
AccountShareService
   ↓
AccountShareRepository
   ↓
T_ACCOUNT_SHARE
```

During creation:

```text
1. Receive submitted form data
2. Create AccountShare object
3. Generate OID
4. Save shareholder
5. Create ApprovalRequest
6. Set PENDING_CHECKER status
```

---

# Spring Security Lifecycle

The authentication flow works as follows:

```text
Login Page
   ↓
Username + Password
   ↓
POST /login
   ↓
Spring Security
   ↓
UserDetailsService
   ↓
Credential Verification
   ↓
AuthenticationSuccessHandler
   ↓
Role-Based Dashboard
```

Detailed process:

```text
1. User opens the login page.

2. User provides username and password.

3. POST /login is executed.

4. Spring Security checks UserDetailsService.

5. Credentials are verified.

6. AuthenticationSuccessHandler is executed.

7. User is redirected according to role.
```

Role mapping:

```text
Maker
   → ROLE_MAKER

Checker
   → ROLE_CHECKER

Approver
   → ROLE_APPROVER
```

---

# Current Authentication Configuration

The current implementation uses Spring Security's configured users.

Example structure:

```java
UserDetails maker =
        User.builder()
                .username("maker")
                .password("{noop}maker123")
                .roles("MAKER")
                .build();
```

The project currently uses role-based Spring Security authentication.

For a production system, credentials should not be stored as plain text or committed to a public repository.

---

# Logout Flow

Logout follows this process:

```text
User clicks Logout
       ↓
/logout
       ↓
Session invalidated
       ↓
Authentication data cleared
       ↓
Login / Main Page
```

---

# Approval Status Lifecycle

Complete request lifecycle:

```text
PENDING_CHECKER
       ↓
PENDING_APPROVER
       ↓
APPROVED
```

If Checker returns the request:

```text
PENDING_CHECKER
       ↓
RETURNED_FOR_MODIFICATION
       ↓
MAKER
```

Shareholder lifecycle:

```text
New Shareholder
       ↓
STATUS = 0
       ↓
Approval Workflow
       ↓
Final Approval
       ↓
STATUS = 1
       ↓
Active Shareholder
```

---

# How to Run the Project

## Prerequisites

Before running the project, install:

- Java JDK
- IntelliJ IDEA
- Git
- Required database server
- Maven support

Verify Java:

```bash
java -version
```

Verify Git:

```bash
git --version
```

---

# Clone the Repository

```bash
git clone https://github.com/Tahmid19710/Share-and-bond-Management-System.git
```

Move into the project directory:

```bash
cd Share-and-bond-Management-System
```

---

# Open in IntelliJ IDEA

Open IntelliJ IDEA.

Select:

```text
File
→ Open
→ Share-and-bond-Management-System
```

Wait for IntelliJ IDEA to:

- Detect the Spring Boot project
- Load Maven dependencies
- Index the project

---

# Database Configuration

Database configuration should be defined inside:

```text
src/main/resources/application.properties
```

or the corresponding Spring configuration file used by the project.

Typical configuration format:

```properties
spring.datasource.url=YOUR_DATABASE_URL
spring.datasource.username=YOUR_DATABASE_USERNAME
spring.datasource.password=YOUR_DATABASE_PASSWORD

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

Use the actual database configuration required by your environment.

Do not publish production database passwords to GitHub.

---

# Run the Application

From IntelliJ IDEA:

```text
Open main Spring Boot application class
        ↓
Click Run
```

Or using Maven:

```bash
mvn spring-boot:run
```

If Maven Wrapper exists:

```bash
./mvnw spring-boot:run
```

On Windows:

```bash
mvnw.cmd spring-boot:run
```

After the Spring Boot application starts, open the configured application URL in the browser.

For a standard local Spring Boot configuration this is commonly:

```text
http://localhost:8080
```

The actual port depends on the project's configuration.

---

# Developer Guide

## Adding a New Field

When adding a new shareholder field, update the system in the following order:

```text
1. Database
2. Entity
3. Service
4. Controller
5. Thymeleaf UI
```

For example:

```text
Database Column
       ↓
Entity Field
       ↓
Business Logic
       ↓
Controller Binding
       ↓
HTML Form
```

This keeps all application layers synchronized.

---

# Adding a New Role

When adding a new user role:

```text
1. Update SecurityConfig
2. Add role permissions
3. Configure URL authorization
4. Create required controller routes
5. Create the role dashboard
6. Test authentication
7. Test authorization
```

---

# Adding a New Workflow Stage

If another approval stage is introduced:

```text
1. Define the new status
2. Define the new current stage
3. Update workflow transition logic
4. Update Controller
5. Update Service
6. Update approval history handling
7. Update Security configuration if a new role is required
8. Update Thymeleaf pages
```

---

# Debugging Guide

When something does not work, debug from the frontend toward the database.

## 1. Frontend

Check:

```text
Form action URL
Input name
Thymeleaf binding
HTTP method
```

---

## 2. Controller

Check:

```text
@RequestMapping
@GetMapping
@PostMapping
Path variables
Request parameters
Method parameters
```

---

## 3. Service

Check:

```text
Business logic
Validation
Status transitions
Entity preparation
```

---

## 4. Repository

Check:

```text
Repository method
Query method
JpaRepository configuration
Entity mapping
```

---

## 5. Database

Finally check:

```text
Table name
Column name
Data type
Primary key
Foreign key
Stored values
```

Recommended debugging sequence:

```text
UI
 ↓
Controller
 ↓
Service
 ↓
Repository
 ↓
Entity
 ↓
Database
```

---

# Git Development Workflow

Before starting development:

```bash
git pull
```

Check repository state:

```bash
git status
```

After making changes:

```bash
git add .
```

Commit:

```bash
git commit -m "Describe the change"
```

Push:

```bash
git push
```

---

# Recommended Git Commit Messages

Use meaningful commit messages.

Examples:

```text
Added shareholder creation workflow

Added checker approval functionality

Added approver dashboard

Added role-based Spring Security

Fixed shareholder update operation

Updated Thymeleaf dashboard

Fixed approval status transition

Added approval action history
```

Avoid messages such as:

```text
update

done

final

changes

fix
```

because they do not explain what actually changed.

---

# Branching Recommendation

For future development, use feature branches.

Example:

```bash
git checkout -b feature/shareholder-search
```

After development:

```bash
git add .
git commit -m "Added shareholder search"
git push -u origin feature/shareholder-search
```

Suggested branch structure:

```text
main
│
├── feature/authentication
├── feature/maker-dashboard
├── feature/checker-workflow
├── feature/approver-workflow
└── feature/shareholder-management
```

---

# Developer Coding Guidelines

## Keep Responsibilities Separated

Do not place all logic inside Controllers.

Recommended:

```text
Controller
    → Request handling

Service
    → Business logic

Repository
    → Database interaction

Entity
    → Database mapping

Thymeleaf
    → Presentation
```

---

## Use Clear Names

Good:

```java
approvalRequest
shareholder
requestId
currentStage
accountShareRepository
```

Avoid unclear names such as:

```java
a
x
data1
obj1
temp
```

---

# Security Guidelines

Developers should:

- Protect URLs according to roles.
- Validate user input.
- Avoid exposing passwords.
- Avoid committing database passwords.
- Avoid committing production secrets.
- Use secure password encoding for production.
- Validate workflow status before approving requests.
- Protect direct access to restricted endpoints.
- Test authentication and authorization separately.

Sensitive configuration should ideally be provided using environment variables or deployment-specific configuration.

---

# Recommended `.gitignore`

A Spring Boot / IntelliJ project should generally ignore development-specific files.

Example:

```gitignore
# IntelliJ IDEA
.idea/
*.iml

# Maven
target/

# Logs
*.log

# OS files
.DS_Store
Thumbs.db

# Local environment
.env

# Local configuration containing credentials
application-local.properties
```

Do not ignore files required to build or understand the application.

---

# Testing Checklist

Before pushing major changes, verify:

```text
[ ] Application builds successfully

[ ] Login works

[ ] Maker login works

[ ] Checker login works

[ ] Approver login works

[ ] Role-based redirects work

[ ] Unauthorized URLs are protected

[ ] Shareholder creation works

[ ] Shareholder list works

[ ] Shareholder update works

[ ] Checker pending list works

[ ] Checker approval works

[ ] Checker return workflow works

[ ] Approver pending list works

[ ] Final approval works

[ ] T_ACCOUNT_SHARE status changes from 0 to 1

[ ] ApprovalRequest status transitions correctly

[ ] ApprovalAction history is created

[ ] Database connection works

[ ] No compilation errors
```

---

# Production Deployment Checklist

Before production deployment:

## Application

```text
[ ] Project builds successfully
[ ] No compilation errors
[ ] Configuration verified
```

## Database

```text
[ ] Database connection tested
[ ] Required tables exist
[ ] Database permissions checked
```

## Security

```text
[ ] User credentials reviewed
[ ] Roles verified
[ ] URL permissions tested
[ ] Production passwords secured
```

## Workflow

```text
[ ] Maker flow tested
[ ] Checker flow tested
[ ] Approver flow tested
```

---

# Future Development

The current system can later be extended with features such as:

- Database-backed user management
- Secure password encryption
- Audit dashboard
- Notification system
- Approval comments
- Transaction management
- REST API layer

These are future improvements and are not part of the currently documented core workflow.

---

# Complete System Lifecycle

```text
Login
  ↓
Role-Based Dashboard
  ↓
Maker Creates Shareholder
  ↓
T_ACCOUNT_SHARE
STATUS = 0
  ↓
Approval Request Created
  ↓
PENDING_CHECKER
  ↓
Checker Reviews
  ↓
PENDING_APPROVER
  ↓
Approver Reviews
  ↓
APPROVED
  ↓
T_ACCOUNT_SHARE
STATUS = 1
  ↓
Active Shareholder
```

---

# Repository

**GitHub Repository**

```text
https://github.com/Tahmid19710/Share-and-bond-Management-System
```

---

# Developer

**Tahmid**

GitHub:

```text
https://github.com/Tahmid19710
```

---

# Project Status

```text
Status: Under Development
```

The core Spring Boot architecture and Maker–Checker–Approver shareholder workflow are implemented.

Additional features and production-level improvements may be added in future versions.

---

# Developer Handover Summary

A developer joining this project should first understand these five components:

```text
1. Spring Security
2. AccountShareController
3. AccountShareService
4. AccountShareRepository
5. Maker–Checker–Approver workflow
```

The most important execution path is:

```text
Thymeleaf
   ↓
Controller
   ↓
Service
   ↓
Repository
   ↓
Entity
   ↓
Database
```

The most important business path is:

```text
Maker
   ↓
Checker
   ↓
Approver
   ↓
Active Shareholder
```

Understanding these two flows provides the foundation required to maintain and extend the application.
