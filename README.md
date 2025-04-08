# Calendar System

This repository contains both the **backend** (Spring Boot) and **frontend** (Vue with Vite) for a simple calendar system.

## Prerequisites

- **JDK 17+** (to run the Spring Boot backend)
- **Node.js** (LTS version) & npm
- **Docker** (To spin up MySQL via Docker)

## Repository Structure

- **backend/**: Java Spring Boot application.
- **client/**: Vue 3 application with Vite.

## 1. Setting Up the Backend

1. **Configure MySQL**  
   - Ensure MySQL is running in Docker. To do it, you can do:
     ```bash
     cd /server
     docker compose up -d
     ```

2. **Flyway Migrations**  
   - On startup, Flyway will run migrations from `src/main/resources/db/migration/`.  
   - Some seed data is included (see `V2__Insert_test_events.sql`).

3. **Build & Run**  
   - Navigate to the **backend** folder:
     ```bash
     cd backend
     ```
   - Build and run with Maven:
     ```bash
     mvn clean install
     mvn spring-boot:run
     ```
   - By default, Spring Boot runs on **port 8080**. Verify at http://localhost:8080.  

## 2. Setting Up the Frontend

1. **Install Dependencies**  
   - Navigate to the **client** folder:
     ```bash
     cd ../client
     ```
   - Install NPM packages:
     ```bash
     npm install
     ```
2. **(Optional) Configure Environment**  
   - To create a custom backend URL, create an `.env` file:
     ```bash
     echo "VITE_API_URL=http://localhost:8080/" > .env
     ```

3. **Run the Dev Server**  
   ```bash
   npm run dev
   ```
   - By default, Vite dev server runs on **port 5173**. Verify at http://localhost:5173.  