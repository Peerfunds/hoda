# hoda


PROJECT/SR INFORMATION
The Member ADR OCI project is a backend application developed using Spring Boot and is part of the broader healthcare domain. Its primary purpose is to handle the intake and processing of encounter detail data for members from external systems. This includes validating, persisting, and updating information related to member encounter records based on configurable business rules.

The project ensures that any incoming data related to ADR (Advance Directives Reporting) is stored, updated, or flagged properly. It acts as a backend microservice within a service-oriented architecture, interacting with a database to store records and exposing RESTful endpoints for other systems to interact with.

DOCUMENT INFORMATION
This document provides a deep technical insight into the Member ADR OCI system. It includes detailed sections covering the architecture, component design, dependencies, database schema, processing logic, and interfaces. Each section aims to assist developers, architects, and QA teams in understanding the structure and functionality of the application.

The intended audience includes software developers, testers, DevOps engineers, and any stakeholders involved in the maintenance or enhancement of the application. This document also serves as a guide for onboarding new team members and for compliance or audit purposes.

COMPONENT DESIGN DETAILS
3.1 Description
This component is responsible for handling encounter data which is submitted via JSON payloads through a REST controller. It parses the data, checks for existing records using unique trace numbers, updates the database accordingly, and returns structured responses to the client. The system uses Spring JPA to perform data access and is structured with a controller-service-repository architecture.

3.2 Component Summary
Technology Stack: Java 17, Spring Boot, Spring JPA, PostgreSQL

Architecture: Microservice

Main Components:

EncounterController

EncounterDetailsService

MemberAdrReportingRepository

MemberAdrReporting Entity

DTO classes for request/response mapping

3.3 Design Considerations
3.3.1 Assumption(s)
All incoming requests will follow a predefined JSON schema.

Each encounter record can be uniquely identified by the traceNumber.

The upstream systems are expected to sanitize and validate the payload before sending.

3.3.2 Security Requirement
The service should be secured via JWT authentication when deployed in production.

Internal access only in UAT/DEV, but protected using Spring Security.

Logs should not store any PII (Personally Identifiable Information).

3.3.3 Performance Criteria
The system should process 100 requests per second with minimal latency (<300ms).

Batch processing can be considered for bulk updates in future releases.

3.3.4 Other Constraints/Issues
Dependency on downstream database availability.

Lack of schema validation in incoming payload can result in inconsistent data.

Dynamic fields in the request payload pose challenges in fixed DTO mapping.

3.3.5 Design Options
Option 1: Static DTO structure with strict schema validation.

Option 2: Flexible schema using Map<String, Object> and runtime field handling (used here).

3.3.6 Design Approach
The system follows a clean separation of concerns with distinct layers: controller (web), service (business), and repository (data). It also implements logging and exception handling using Spring’s standard @ControllerAdvice.

3.3.7 Consequences of Design Choices
Using dynamic mapping provides flexibility but limits compile-time validation. If field names change, issues will surface only at runtime. Hence, it requires strong test coverage and field presence checks.

3.4 Traceability
The system allows traceability using the traceNumber field. Each update or insertion of member encounter data is logged and traceable via unique trace identifiers. Additionally, logs and database records maintain timestamps (insertDatetime, updateDatetime) to audit changes.

3.5 Internal Composition
Internally, the application has the following structure:

Controller: Entry point for requests.

Service: Handles the business logic and validation.

Repository: Interfaces with the database using Spring JPA.

Entities: Java objects mapped to database tables.

DTOs: Define request and response structure.

Utility: Logging and response handling.

3.6 External Dependencies
Spring Boot Starter Web: For REST API handling.

Spring Data JPA: For database interaction.

Lombok: To reduce boilerplate code.

PostgreSQL Driver: To connect with the database.

Logback: For structured logging.

3.7 Disaster Backup and Recovery
Daily backups of the PostgreSQL database are automated.

Application logs are rotated and archived weekly.

In case of failure, the service can be redeployed within 5 minutes using Docker/Kubernetes.

3.8 Processing
3.8.1 Description of the Process
The process begins when the external system submits encounter detail data. The system validates the data, checks if the traceNumber already exists, and updates the corresponding row or creates a new one.

3.8.2 Graphical Representation of the Process
plaintext
Copy
Edit
Client -> POST /encounter
          |
       Validate
          |
Check if traceNumber exists --> Yes --> Update record
                             --> No  --> Insert new record
          |
     Return JSON Response
3.8.3 Error/Edit Handling
If the traceNumber is missing: return 400 Bad Request.

If isDocumentUpdate is not a boolean: return 422 Unprocessable Entity.

If record is not found during update: return 404 Not Found.

3.8.4 Input/Output/ Message Parameters
Input: JSON payload with fields like traceNumber, isDocumentUpdate, etc.

Output: Standard JSON response with status, message, and traceNumber.

3.9 Database Considerations
3.9.1 Efficiency Concerns
Indexes are used on traceNumber for fast lookup.

Only necessary fields are fetched using projections where needed.

Timestamps are automatically managed for auditing.

3.9.2 CRUD Matrix
Operation	Entity	Method
CREATE	MemberAdrReporting	save()
READ	MemberAdrReporting	findByTraceNumber()
UPDATE	MemberAdrReporting	save() if exists
DELETE	Not implemented	-

3.10 Data Conversion
All input data is received in JSON and converted into entity format using Jackson and Java models. Dates are parsed and handled using LocalDateTime.

3.11 User Interfaces
Currently, no user interface is bundled with the backend. A simple Swagger interface can be enabled for testing REST endpoints. Alternatively, Postman is used for testing payload submissions.

3.12 Interfaces Listed
/generate-content: POST - To invoke GenAI content generation (if integrated)

/encounter: POST - To ingest encounter data

3.13 Interfaces Provided
The backend service provides RESTful endpoints secured via headers (authentication to be added later). All responses follow a common structure using ApiResponse.

3.14 Reports
Currently, structured JSON reports are generated and returned via APIs. Future versions can export this data to CSV, Excel, or even PDFs for regulatory submissions.
