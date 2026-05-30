# Module 2  MySQL SQL Exercises

This module contains three self-contained database projects designed for hands-on SQL practice. Each project includes table creation scripts, sample data, and a variety of queries progressing from basic to advanced.

---

## Project Overview

| # | Project | Directory | Files | Focus Areas |
|---|---------|-----------|-------|-------------|
| 1 | Employee Management | employee-management/ | schema.sql, dml.sql, queries.sql | JOINs, subqueries, self-joins, window functions, aggregates |
| 2 | Student Database | student-database/ | schema.sql, queries.sql | GPA calculation, honor roll, enrollment analytics |
| 3 | Order Management | order-management/ | schema.sql, queries.sql | Revenue reports, customer segmentation, nested subqueries |

---

## 1. Employee Management System

**Purpose:** Model a company's employee structure with departments, projects, and salary tracking.

### Tables & Relationships


departments < employees < salary_history
                    
                     manager_id (self-referencing FK)
                  
                  < employee_projects > projects


| Table | Description |
|-------|-------------|
| departments | Company departments (Engineering, Marketing, etc.) |
| employees | Employee records with self-referencing manager relationship |
| projects | Company projects with budget tracking |
| employee_projects | Many-to-many mapping (composite PK) |
| salary_history | Audit trail for every salary change |

### Files
- **schema.sql**  DROP/CREATE database, all tables with constraints, indexes
- **dml.sql**  INSERT sample data (5 depts, 15 employees, 5 projects, 14 assignments, 7 salary records), plus UPDATE and DELETE examples
- **queries.sql**  25+ practice queries covering WHERE, ORDER BY, LIMIT, all JOIN types, GROUP BY/HAVING, subqueries (correlated & non-correlated), window functions (ROW_NUMBER, DENSE_RANK, running totals), CASE, COALESCE, date functions, and UNION

---

## 2. Student Database System

**Purpose:** Track student enrollments, course grades, and calculate GPA.

### Tables & Relationships


students < enrollments > courses


| Table | Description |
|-------|-------------|
| students | Student profiles with major |
| courses | Course catalog with credits and instructor |
| enrollments | Links students to courses with semester and grade |

### Files
- **schema.sql**  Database schema + sample data (8 students, 6 courses, 18 enrollments)
- **queries.sql**  16 practice queries including weighted GPA calculation, honor roll identification, grade distribution analysis, credit tracking, and instructor performance ranking

---

## 3. Order Management System

**Purpose:** E-commerce order tracking with customers, products, orders, and payments.

### Tables & Relationships


customers < orders < order_items > products
                 
                 < payments


| Table | Description |
|-------|-------------|
| customers | Customer profiles with city |
| products | Product catalog with category, price, and stock |
| orders | Order headers with status tracking |
| order_items | Individual line items per order |
| payments | Payment records with method |

### Files
- **schema.sql**  Database schema + sample data (8 customers, 10 products, 12 orders, 22 line items, 10 payments)
- **queries.sql**  18 practice queries covering monthly revenue reports, top customers, best-selling products, category analysis, customer segmentation, month-over-month growth (window functions), unpaid order detection, and complex nested subqueries

---

## How to Execute

### Option A: MySQL Command Line

bash
# Navigate to the project directory, then:
mysql -u root -p < employee-management/schema.sql
mysql -u root -p employee_management < employee-management/dml.sql
mysql -u root -p employee_management < employee-management/queries.sql

mysql -u root -p < student-database/schema.sql
mysql -u root -p student_database < student-database/queries.sql

mysql -u root -p < order-management/schema.sql
mysql -u root -p order_management < order-management/queries.sql


### Option B: MySQL Workbench

1. Open MySQL Workbench and connect to your local server
2. Go to **File  Open SQL Script**
3. Open the schema.sql file first and execute it ( icon)
4. Then open and execute dml.sql (for employee-management)
5. Finally, open queries.sql and run queries individually or in batches

### Option C: From within a MySQL session

sql
SOURCE d:/Java FullStack Tasks/module-2/mysql/employee-management/schema.sql;
SOURCE d:/Java FullStack Tasks/module-2/mysql/employee-management/dml.sql;


---

## Prerequisites

- **MySQL 8.0+** (required for window functions like ROW_NUMBER, DENSE_RANK, LAG)
- Any MySQL client: MySQL CLI, MySQL Workbench, DBeaver, or VS Code with MySQL extension

---

## SQL Concepts Covered

| Category | Topics |
|----------|--------|
| **DDL** | CREATE TABLE, DROP DATABASE, ALTER TABLE, constraints (PK, FK, UNIQUE, CHECK, NOT NULL, DEFAULT), indexes |
| **DML** | INSERT, UPDATE, DELETE with WHERE clauses |
| **Queries** | SELECT, WHERE, ORDER BY, LIMIT, DISTINCT |
| **Joins** | INNER JOIN, LEFT JOIN, RIGHT JOIN, self-join |
| **Aggregates** | COUNT, SUM, AVG, MAX, MIN, GROUP BY, HAVING |
| **Subqueries** | Non-correlated, correlated, IN, EXISTS, NOT EXISTS, derived tables |
| **Advanced** | Window functions (ROW_NUMBER, DENSE_RANK, LAG, running totals), CASE, COALESCE, UNION |
| **Functions** | CONCAT, ROUND, DATE_FORMAT, YEAR, MONTH, DATEDIFF, DATE_SUB, CURDATE |
