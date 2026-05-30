# Core Java Exercises  Digital Nurture 5.0 (Module 3)

A comprehensive set of Core Java exercises covering fundamentals through modern Java 21 features, built as a Maven project.

## Project Structure


core-java/
 pom.xml
 README.md
 src/main/java/com/upskill/javafse/
     basics/           Foundational Java concepts
     oop/              Object-Oriented Programming
     collections/      Collections Framework & Streams
     exceptions/       Exception handling patterns
     fileio/           File I/O and NIO
     threading/        Multithreading & concurrency
     jdbc/             Database operations with JDBC
     modern/           Java 1721 features


## Package Overview

### basics
Core programming constructs  arithmetic calculator, palindrome & prime checking, recursion (factorial, fibonacci, Tower of Hanoi), array operations (sorting, searching, rotation), and string manipulation.

### oop
Three mini-systems demonstrating OOP principles:
- **Banking System**  Abstract Account class with SavingsAccount and CurrentAccount subclasses, demonstrating inheritance and polymorphism.
- **Employee Management**  Abstract Employee with Manager and Developer types, payroll calculations, and department management.
- **Library System**  Borrowable interface, Book and Member classes, and a Library manager with search capabilities.

### collections
Hands-on with List, Set, Map implementations, Comparable/Comparator sorting (including lambda-based), and the Stream API (filter, map, reduce, collect, groupingBy, etc.).

### exceptions
Custom exception classes (InsufficientBalanceException, InvalidAgeException), input validation, file exception handling, and try-with-resources with custom AutoCloseable resources.

### fileio
Reading and writing files with BufferedReader/BufferedWriter, object serialization, buffered vs. unbuffered performance comparisons, and NIO.2 operations (Path, Files.walk, Files.copy).

### threading
Producer-Consumer with BlockingQueue, synchronized counters (race condition demo), thread lifecycle and states, and ExecutorService with Callable/Future.

### jdbc
Full CRUD via EmployeeDAO, PreparedStatement usage, connection management, and transaction handling (commit, rollback, savepoints).

### modern
Java 1721 features: records, switch expressions with pattern matching, text blocks, sealed classes/interfaces, and virtual threads.

## Prerequisites

- **Java 21** (or later)
- **Apache Maven 3.9+**
- **MySQL 8.x** (only required for the jdbc package  update credentials in DatabaseConnection.java)

## Build & Run

bash
# Compile all exercises
mvn compile

# Run a specific class
mvn exec:java -Dexec.mainClass="com.upskill.javafse.basics.Calculator"

# Or use java directly after compiling
java -cp target/classes com.upskill.javafse.basics.PalindromeChecker


## JDBC Setup

Before running the jdbc package, create the database and table:

sql
CREATE DATABASE IF NOT EXISTS company_db;
USE company_db;

CREATE TABLE employees (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    department VARCHAR(50),
    salary DOUBLE
);


Then update the connection details in DatabaseConnection.java to match your MySQL setup.

## Dependencies

| Dependency | Version | Purpose |
|---|---|---|
| mysql-connector-j | 8.3.0 | MySQL JDBC driver |
| junit-jupiter | 5.10.2 | Unit testing (test scope) |

## Notes

- Each class has a main method so it can be run independently.
- Code is written at a student-friendly level  not enterprise-grade.
- Some classes use var for local variable type inference; others use explicit types for clarity.
- The exercises are self-contained; they don't depend on each other across packages.
