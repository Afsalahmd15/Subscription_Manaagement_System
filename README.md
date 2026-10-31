# Brightloop – Subscription Management Platform

Brightloop is a Spring Boot-based subscription management platform backed by PostgreSQL. It provides functionality for managing users, products, product variants, and subscription plans.

## Tech Stack

- **Backend:** Java, Spring Boot
- **Frontend:** HTML, CSS, Thymeleaf
- **Database:** PostgreSQL
- **Database Connectivity:** JDBC (`PreparedStatement`)
- **Build Tool:** Maven

## Prerequisites

Make sure the following are installed:

- Java 21 or compatible version specified in `pom.xml`
- PostgreSQL 17 or compatible version
- Git
- Maven (optional, since the project includes Maven Wrapper)

Verify the installations:

```bash
java -version
psql --version
git --version
```

## 1. Clone the Repository

```bash
git clone <REPOSITORY_URL>
cd brightloop
```

Replace `<REPOSITORY_URL>` with the Git repository URL.

## 2. Create the PostgreSQL Database

Open PostgreSQL:

```bash
sudo -u postgres psql
```

Create a database:

```sql
CREATE DATABASE brightloop;
```

Connect to it:

```sql
\c brightloop
```

## 3. Create Database Tables

Execute the following SQL statements inside the `brightloop` database.

### Users

```sql
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL
);
```

### Email Verifications

```sql
CREATE TABLE email_verifications (
    id SERIAL PRIMARY KEY,
    user_id INT REFERENCES users(id) ON DELETE CASCADE,
    token VARCHAR(255) UNIQUE NOT NULL,
    expires_at TIMESTAMP NOT NULL
);
```

### Password Resets

```sql
CREATE TABLE password_resets (
    id SERIAL PRIMARY KEY,
    user_id INT REFERENCES users(id) ON DELETE CASCADE,
   
