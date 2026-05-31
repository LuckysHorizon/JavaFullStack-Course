-- ============================================
-- Employee Management System - Practice Queries
-- 25+ queries covering a wide range of SQL
-- ============================================

USE employee_management;

-- -------------------------------------------
-- 1. All employees ordered by hire date
-- -------------------------------------------
SELECT emp_id, first_name, last_name, hire_date, salary
FROM employees
ORDER BY hire_date ASC;

-- -------------------------------------------
-- 2. Employees in the Engineering department
-- -------------------------------------------
SELECT e.emp_id, e.first_name, e.last_name, e.salary
FROM employees e
INNER JOIN departments d ON e.dept_id = d.dept_id
WHERE d.dept_name = 'Engineering';

-- -------------------------------------------
-- 3. Employees earning above the average salary
-- -------------------------------------------
SELECT first_name, last_name, salary
FROM employees
WHERE salary > (SELECT AVG(salary) FROM employees)
ORDER BY salary DESC;

-- -------------------------------------------
-- 4. Employee count per department
-- -------------------------------------------
SELECT d.dept_name, COUNT(e.emp_id) AS employee_count
FROM departments d
LEFT JOIN employees e ON d.dept_id = e.dept_id
GROUP BY d.dept_name
ORDER BY employee_count DESC;

-- -------------------------------------------
-- 5. Department with the highest total salary
-- -------------------------------------------
SELECT d.dept_name, SUM(e.salary) AS total_salary
FROM departments d
INNER JOIN employees e ON d.dept_id = e.dept_id
GROUP BY d.dept_name
ORDER BY total_salary DESC
LIMIT 1;

-- -------------------------------------------
-- 6. INNER JOIN  employees with their
--    department details
-- -------------------------------------------
SELECT
  e.emp_id,
  CONCAT(e.first_name, ' ', e.last_name) AS full_name,
  e.salary,
  d.dept_name,
  d.location
FROM employees e
INNER JOIN departments d ON e.dept_id = d.dept_id
ORDER BY d.dept_name, e.last_name;

-- -------------------------------------------
-- 7. LEFT JOIN  show all departments, even
--    those with no employees assigned
-- -------------------------------------------
SELECT d.dept_name, e.first_name, e.last_name
FROM departments d
LEFT JOIN employees e ON d.dept_id = e.dept_id
ORDER BY d.dept_name;

-- -------------------------------------------
-- 8. Self-join  employees with their
--    manager's name
-- -------------------------------------------
SELECT
  e.emp_id,
  CONCAT(e.first_name, ' ', e.last_name)   AS employee_name,
  CONCAT(m.first_name, ' ', m.last_name)   AS manager_name
FROM employees e
LEFT JOIN employees m ON e.manager_id = m.emp_id
ORDER BY e.emp_id;

-- -------------------------------------------
-- 9. Second highest salary
--    (classic interview question)
-- -------------------------------------------

-- Approach A: using LIMIT with OFFSET
SELECT DISTINCT salary
FROM employees
ORDER BY salary DESC
LIMIT 1 OFFSET 1;

-- Approach B: using a subquery
SELECT MAX(salary) AS second_highest_salary
FROM employees
WHERE salary < (SELECT MAX(salary) FROM employees);

-- -------------------------------------------
-- 10. Top 3 highest-paid employees
-- -------------------------------------------
SELECT first_name, last_name, salary
FROM employees
ORDER BY salary DESC
LIMIT 3;

-- -------------------------------------------
-- 11. Employees hired in the last 2 years
-- -------------------------------------------
SELECT first_name, last_name, hire_date
FROM employees
WHERE hire_date >= DATE_SUB(CURDATE(), INTERVAL 2 YEAR)
ORDER BY hire_date DESC;

-- -------------------------------------------
-- 12. GROUP BY with HAVING  departments
--     having more than 2 employees
-- -------------------------------------------
SELECT d.dept_name, COUNT(e.emp_id) AS emp_count
FROM departments d
INNER JOIN employees e ON d.dept_id = e.dept_id
GROUP BY d.dept_name
HAVING emp_count > 2
ORDER BY emp_count DESC;

-- -------------------------------------------
-- 13. Subquery  employees earning more than
--     their department's average salary
-- -------------------------------------------
SELECT e.first_name, e.last_name, e.salary, d.dept_name
FROM employees e
INNER JOIN departments d ON e.dept_id = d.dept_id
WHERE e.salary > (
  SELECT AVG(e2.salary)
  FROM employees e2
  WHERE e2.dept_id = e.dept_id
)
ORDER BY d.dept_name, e.salary DESC;

-- -------------------------------------------
-- 14. Correlated subquery  find the Nth
--     highest salary (here N = 3)
-- -------------------------------------------
SELECT DISTINCT salary
FROM employees e1
WHERE 3 = (
  SELECT COUNT(DISTINCT salary)
  FROM employees e2
  WHERE e2.salary >= e1.salary
);

-- -------------------------------------------
-- 15. Aggregates  SUM, AVG, COUNT per dept
-- -------------------------------------------
SELECT
  d.dept_name,
  COUNT(e.emp_id)    AS total_employees,
  SUM(e.salary)      AS total_salary,
  ROUND(AVG(e.salary), 2) AS avg_salary,
  MAX(e.salary)      AS max_salary,
  MIN(e.salary)      AS min_salary
FROM departments d
LEFT JOIN employees e ON d.dept_id = e.dept_id
GROUP BY d.dept_name
ORDER BY total_salary DESC;

-- -------------------------------------------
-- 16. BETWEEN  employees with salary in a
--     specific range
-- -------------------------------------------
SELECT first_name, last_name, salary
FROM employees
WHERE salary BETWEEN 60000 AND 90000
ORDER BY salary;

-- -------------------------------------------
-- 17. LIKE  name pattern matching
-- -------------------------------------------

-- Employees whose last name starts with 'S'
SELECT first_name, last_name
FROM employees
WHERE last_name LIKE 'S%';

-- Employees whose first name contains 'an'
SELECT first_name, last_name
FROM employees
WHERE first_name LIKE '%an%';

-- -------------------------------------------
-- 18. IN clause with a subquery
--     Employees who are assigned to at least
--     one project
-- -------------------------------------------
SELECT first_name, last_name
FROM employees
WHERE emp_id IN (
  SELECT DISTINCT emp_id
  FROM employee_projects
);

-- -------------------------------------------
-- 19. EXISTS  same idea as #18, but using
--     EXISTS (often more efficient on large
--     datasets)
-- -------------------------------------------
SELECT e.first_name, e.last_name
FROM employees e
WHERE EXISTS (
  SELECT 1
  FROM employee_projects ep
  WHERE ep.emp_id = e.emp_id
);

-- -------------------------------------------
-- 20. CASE expression  salary brackets
-- -------------------------------------------
SELECT
  first_name,
  last_name,
  salary,
  CASE
    WHEN salary >= 100000 THEN 'Senior'
    WHEN salary >= 70000  THEN 'Mid-Level'
    WHEN salary >= 50000  THEN 'Junior'
    ELSE 'Trainee'
  END AS salary_bracket
FROM employees
ORDER BY salary DESC;

-- -------------------------------------------
-- 21. Window function  running total of
--     salaries ordered by hire date
--     (requires MySQL 8.0+)
-- -------------------------------------------
SELECT
  emp_id,
  CONCAT(first_name, ' ', last_name) AS full_name,
  hire_date,
  salary,
  SUM(salary) OVER (ORDER BY hire_date) AS running_total,
  ROW_NUMBER() OVER (ORDER BY hire_date) AS hire_order
FROM employees
ORDER BY hire_date;

-- -------------------------------------------
-- 22. COALESCE  handle NULL manager_id
-- -------------------------------------------
SELECT
  e.first_name,
  e.last_name,
  COALESCE(CONCAT(m.first_name, ' ', m.last_name), 'No Manager') AS manager_name
FROM employees e
LEFT JOIN employees m ON e.manager_id = m.emp_id
ORDER BY e.emp_id;

-- -------------------------------------------
-- 23. DATE functions  YEAR, MONTH, DATEDIFF
-- -------------------------------------------

-- Employees hired in 2020
SELECT first_name, last_name, hire_date
FROM employees
WHERE YEAR(hire_date) = 2020;

-- Employees hired in the month of March
SELECT first_name, last_name, hire_date
FROM employees
WHERE MONTH(hire_date) = 3;

-- How many days each employee has been with the company
SELECT
  first_name,
  last_name,
  hire_date,
  DATEDIFF(CURDATE(), hire_date) AS days_employed,
  ROUND(DATEDIFF(CURDATE(), hire_date) / 365.25, 1) AS years_employed
FROM employees
ORDER BY days_employed DESC;

-- -------------------------------------------
-- 24. Multi-table join  employees, their
--     departments, and assigned projects
-- -------------------------------------------
SELECT
  CONCAT(e.first_name, ' ', e.last_name) AS employee,
  d.dept_name,
  p.project_name,
  ep.role,
  p.budget
FROM employees e
INNER JOIN departments d       ON e.dept_id = d.dept_id
INNER JOIN employee_projects ep ON e.emp_id = ep.emp_id
INNER JOIN projects p          ON ep.project_id = p.project_id
ORDER BY d.dept_name, e.last_name;

-- -------------------------------------------
-- 25. UNION  combine active Engineering
--     employees with all managers
-- -------------------------------------------
SELECT first_name, last_name, 'Engineering Employee' AS category
FROM employees
WHERE dept_id = 1 AND status = 'active'

UNION

SELECT first_name, last_name, 'Manager' AS category
FROM employees
WHERE emp_id IN (
  SELECT DISTINCT manager_id
  FROM employees
  WHERE manager_id IS NOT NULL
);

-- -------------------------------------------
-- BONUS: Salary history with employee details
-- -------------------------------------------
SELECT
  CONCAT(e.first_name, ' ', e.last_name) AS employee,
  sh.old_salary,
  sh.new_salary,
  (sh.new_salary - sh.old_salary) AS raise_amount,
  ROUND(((sh.new_salary - sh.old_salary) / sh.old_salary) * 100, 1) AS raise_pct,
  sh.change_date
FROM salary_history sh
INNER JOIN employees e ON sh.emp_id = e.emp_id
ORDER BY sh.change_date DESC;

-- -------------------------------------------
-- BONUS: RIGHT JOIN  all projects and any
--        assigned employees
-- -------------------------------------------
SELECT
  p.project_name,
  CONCAT(e.first_name, ' ', e.last_name) AS employee,
  ep.role
FROM employees e
RIGHT JOIN employee_projects ep ON e.emp_id = ep.emp_id
RIGHT JOIN projects p           ON ep.project_id = p.project_id
ORDER BY p.project_name;

-- -------------------------------------------
-- BONUS: Dense Rank  rank employees by
--        salary within each department
-- -------------------------------------------
SELECT
  d.dept_name,
  CONCAT(e.first_name, ' ', e.last_name) AS employee,
  e.salary,
  DENSE_RANK() OVER (PARTITION BY d.dept_name ORDER BY e.salary DESC) AS dept_rank
FROM employees e
INNER JOIN departments d ON e.dept_id = d.dept_id
ORDER BY d.dept_name, dept_rank;
