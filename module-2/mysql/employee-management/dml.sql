-- ============================================
-- Employee Management System - DML
-- Sample data, updates, and deletes
-- ============================================

USE employee_management;

-- -------------------------------------------
-- INSERT departments
-- -------------------------------------------
INSERT INTO departments (dept_name, location) VALUES
  ('Engineering',  'Building A - Floor 3'),
  ('Marketing',    'Building B - Floor 1'),
  ('Sales',        'Building B - Floor 2'),
  ('HR',           'Building A - Floor 1'),
  ('Finance',      'Building C - Floor 1');

-- -------------------------------------------
-- INSERT employees
-- Note: managers are inserted first (manager_id NULL),
-- then employees referencing them
-- -------------------------------------------

-- Senior staff / managers (no manager_id yet)
INSERT INTO employees (first_name, last_name, email, phone, hire_date, salary, dept_id, manager_id, status) VALUES
  ('Rajesh',   'Kumar',     'rajesh.kumar@company.com',     '9876543210', '2018-03-15', 115000.00, 1, NULL,  'active'),
  ('Priya',    'Sharma',    'priya.sharma@company.com',     '9876543211', '2017-06-20', 105000.00, 2, NULL,  'active'),
  ('Amit',     'Patel',     'amit.patel@company.com',       '9876543212', '2019-01-10', 98000.00,  3, NULL,  'active'),
  ('Sneha',    'Reddy',     'sneha.reddy@company.com',      '9876543213', '2018-09-05', 102000.00, 4, NULL,  'active'),
  ('Vikram',   'Singh',     'vikram.singh@company.com',     '9876543214', '2016-11-28', 120000.00, 5, NULL,  'active');

-- Regular employees (with manager references)
INSERT INTO employees (first_name, last_name, email, phone, hire_date, salary, dept_id, manager_id, status) VALUES
  ('Ananya',   'Gupta',     'ananya.gupta@company.com',     '9876543215', '2020-07-12', 72000.00,  1, 1,  'active'),
  ('Rohan',    'Mehta',     'rohan.mehta@company.com',       '9876543216', '2021-02-01', 68000.00,  1, 1,  'active'),
  ('Kavita',   'Nair',      'kavita.nair@company.com',       '9876543217', '2020-11-18', 65000.00,  2, 2,  'active'),
  ('Suresh',   'Iyer',      'suresh.iyer@company.com',       '9876543218', '2022-04-25', 58000.00,  3, 3,  'active'),
  ('Deepa',    'Joshi',     'deepa.joshi@company.com',       '9876543219', '2021-08-30', 62000.00,  3, 3,  'active'),
  ('Arjun',    'Rao',       'arjun.rao@company.com',         '9876543220', '2023-01-15', 55000.00,  1, 1,  'active'),
  ('Meera',    'Verma',     'meera.verma@company.com',       '9876543221', '2022-06-10', 71000.00,  4, 4,  'on_leave'),
  ('Karthik',  'Sundaram',  'karthik.sundaram@company.com',  '9876543222', '2019-12-01', 88000.00,  5, 5,  'active'),
  ('Pooja',    'Desai',     'pooja.desai@company.com',       '9876543223', '2023-05-20', 48000.00,  2, 2,  'active'),
  ('Nikhil',   'Chopra',    'nikhil.chopra@company.com',     '9876543224', '2020-03-08', 76000.00,  1, 1,  'inactive');

-- -------------------------------------------
-- INSERT projects
-- -------------------------------------------
INSERT INTO projects (project_name, start_date, end_date, budget) VALUES
  ('Cloud Migration',        '2024-01-10', '2024-12-31', 500000.00),
  ('Mobile App Redesign',    '2024-03-01', '2024-09-30', 250000.00),
  ('CRM Integration',        '2024-02-15', '2024-08-15', 180000.00),
  ('Data Analytics Platform', '2024-06-01', '2025-06-01', 750000.00),
  ('Website Revamp',         '2024-04-01', '2024-10-31', 120000.00);

-- -------------------------------------------
-- INSERT employee_projects assignments
-- -------------------------------------------
INSERT INTO employee_projects (emp_id, project_id, role) VALUES
  (1,  1, 'Project Lead'),
  (6,  1, 'Developer'),
  (7,  1, 'Developer'),
  (11, 1, 'Junior Developer'),
  (2,  5, 'Marketing Lead'),
  (8,  5, 'Content Strategist'),
  (14, 5, 'Designer'),
  (3,  3, 'Sales Coordinator'),
  (9,  3, 'Analyst'),
  (10, 3, 'Analyst'),
  (13, 4, 'Data Engineer'),
  (5,  4, 'Finance Advisor'),
  (6,  2, 'Lead Developer'),
  (7,  2, 'Backend Developer');

-- -------------------------------------------
-- INSERT salary_history entries
-- -------------------------------------------
INSERT INTO salary_history (emp_id, old_salary, new_salary, change_date) VALUES
  (1,  105000.00, 115000.00, '2023-04-01 10:00:00'),
  (2,  95000.00,  105000.00, '2023-04-01 10:00:00'),
  (6,  65000.00,  72000.00,  '2023-07-15 09:30:00'),
  (7,  60000.00,  68000.00,  '2023-07-15 09:30:00'),
  (13, 80000.00,  88000.00,  '2022-12-01 11:00:00'),
  (9,  52000.00,  58000.00,  '2024-01-10 14:00:00'),
  (5,  110000.00, 120000.00, '2023-01-05 10:00:00');

-- ============================================
-- UPDATE examples
-- ============================================

-- 1. Give Ananya Gupta a salary raise
UPDATE employees
SET salary = 78000.00
WHERE emp_id = 6;

-- Record the raise in salary_history
INSERT INTO salary_history (emp_id, old_salary, new_salary)
VALUES (6, 72000.00, 78000.00);

-- 2. Transfer Suresh Iyer from Sales to Engineering
UPDATE employees
SET dept_id = 1, manager_id = 1
WHERE emp_id = 9;

-- 3. Change Meera Verma's status from on_leave to active
UPDATE employees
SET status = 'active'
WHERE emp_id = 12;

-- ============================================
-- DELETE examples
-- ============================================

-- 1. Remove a specific employee_project assignment
--    (Nikhil Chopra is inactive, remove from Cloud Migration)
DELETE FROM employee_projects
WHERE emp_id = 15 AND project_id = 1;

-- 2. Delete a salary history record (correcting a data entry mistake)
DELETE FROM salary_history
WHERE history_id = 7
  AND emp_id = 5;
