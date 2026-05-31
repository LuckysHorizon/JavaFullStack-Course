-- ============================================
-- Employee Management System - Schema
-- Database: employee_management
-- ============================================

DROP DATABASE IF EXISTS employee_management;
CREATE DATABASE employee_management;
USE employee_management;

-- -------------------------------------------
-- Table: departments
-- Stores company department information
-- -------------------------------------------
CREATE TABLE departments (
  dept_id     INT           AUTO_INCREMENT,
  dept_name   VARCHAR(100)  NOT NULL UNIQUE,
  location    VARCHAR(100),
  created_at  TIMESTAMP     DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (dept_id)
);

-- -------------------------------------------
-- Table: employees
-- Core employee records with self-referencing
-- manager relationship
-- -------------------------------------------
CREATE TABLE employees (
  emp_id      INT             AUTO_INCREMENT,
  first_name  VARCHAR(50)     NOT NULL,
  last_name   VARCHAR(50)     NOT NULL,
  email       VARCHAR(100)    NOT NULL UNIQUE,
  phone       VARCHAR(15),
  hire_date   DATE            NOT NULL,
  salary      DECIMAL(10, 2)  CHECK (salary > 0),
  dept_id     INT,
  manager_id  INT,
  status      ENUM('active', 'inactive', 'on_leave') DEFAULT 'active',
  PRIMARY KEY (emp_id),
  FOREIGN KEY (dept_id)     REFERENCES departments(dept_id)
    ON DELETE SET NULL ON UPDATE CASCADE,
  FOREIGN KEY (manager_id)  REFERENCES employees(emp_id)
    ON DELETE SET NULL ON UPDATE CASCADE
);

-- -------------------------------------------
-- Table: projects
-- Company projects with budget tracking
-- -------------------------------------------
CREATE TABLE projects (
  project_id    INT             AUTO_INCREMENT,
  project_name  VARCHAR(150)    NOT NULL,
  start_date    DATE,
  end_date      DATE,
  budget        DECIMAL(12, 2),
  PRIMARY KEY (project_id)
);

-- -------------------------------------------
-- Table: employee_projects
-- Many-to-many mapping between employees
-- and projects, with a role on each assignment
-- -------------------------------------------
CREATE TABLE employee_projects (
  emp_id      INT,
  project_id  INT,
  role        VARCHAR(50),
  PRIMARY KEY (emp_id, project_id),
  FOREIGN KEY (emp_id)      REFERENCES employees(emp_id)
    ON DELETE CASCADE ON UPDATE CASCADE,
  FOREIGN KEY (project_id)  REFERENCES projects(project_id)
    ON DELETE CASCADE ON UPDATE CASCADE
);

-- -------------------------------------------
-- Table: salary_history
-- Audit trail for every salary change
-- -------------------------------------------
CREATE TABLE salary_history (
  history_id   INT             AUTO_INCREMENT,
  emp_id       INT             NOT NULL,
  old_salary   DECIMAL(10, 2),
  new_salary   DECIMAL(10, 2),
  change_date  TIMESTAMP       DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (history_id),
  FOREIGN KEY (emp_id)  REFERENCES employees(emp_id)
    ON DELETE CASCADE ON UPDATE CASCADE
);

-- -------------------------------------------
-- Indexes for frequently queried columns
-- -------------------------------------------
CREATE INDEX idx_emp_email     ON employees(email);
CREATE INDEX idx_emp_dept_id   ON employees(dept_id);
CREATE INDEX idx_emp_last_name ON employees(last_name);
CREATE INDEX idx_emp_hire_date ON employees(hire_date);
