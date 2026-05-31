-- ============================================
-- Student Database System - Schema & Data
-- Database: student_database
-- ============================================

DROP DATABASE IF EXISTS student_database;
CREATE DATABASE student_database;
USE student_database;

-- -------------------------------------------
-- Table: students
-- -------------------------------------------
CREATE TABLE students (
  student_id      INT           AUTO_INCREMENT,
  first_name      VARCHAR(50)   NOT NULL,
  last_name       VARCHAR(50)   NOT NULL,
  email           VARCHAR(100)  NOT NULL UNIQUE,
  enrollment_date DATE          NOT NULL,
  major           VARCHAR(100),
  PRIMARY KEY (student_id)
);

-- -------------------------------------------
-- Table: courses
-- -------------------------------------------
CREATE TABLE courses (
  course_id    INT           AUTO_INCREMENT,
  course_name  VARCHAR(150)  NOT NULL,
  credits      INT           NOT NULL CHECK (credits > 0),
  instructor   VARCHAR(100),
  PRIMARY KEY (course_id)
);

-- -------------------------------------------
-- Table: enrollments
-- Links students to courses with a grade
-- -------------------------------------------
CREATE TABLE enrollments (
  enrollment_id  INT           AUTO_INCREMENT,
  student_id     INT           NOT NULL,
  course_id      INT           NOT NULL,
  semester       VARCHAR(20)   NOT NULL,
  grade          CHAR(2),
  PRIMARY KEY (enrollment_id),
  FOREIGN KEY (student_id) REFERENCES students(student_id)
    ON DELETE CASCADE ON UPDATE CASCADE,
  FOREIGN KEY (course_id)  REFERENCES courses(course_id)
    ON DELETE CASCADE ON UPDATE CASCADE,
  UNIQUE (student_id, course_id, semester)
);

-- Indexes
CREATE INDEX idx_enroll_student ON enrollments(student_id);
CREATE INDEX idx_enroll_course  ON enrollments(course_id);

-- ============================================
-- Sample Data
-- ============================================

-- Students (8 students with different majors)
INSERT INTO students (first_name, last_name, email, enrollment_date, major) VALUES
  ('Aditya',   'Bhatt',     'aditya.bhatt@university.edu',    '2022-08-15', 'Computer Science'),
  ('Fatima',   'Khan',      'fatima.khan@university.edu',     '2022-08-15', 'Mathematics'),
  ('Rahul',    'Menon',     'rahul.menon@university.edu',     '2023-01-10', 'Computer Science'),
  ('Ishita',   'Das',       'ishita.das@university.edu',      '2023-01-10', 'Physics'),
  ('Siddharth','Jain',      'siddharth.jain@university.edu',  '2023-08-20', 'Computer Science'),
  ('Nisha',    'Agarwal',   'nisha.agarwal@university.edu',   '2023-08-20', 'English Literature'),
  ('Omar',     'Sheikh',    'omar.sheikh@university.edu',     '2024-01-08', 'Mathematics'),
  ('Tanvi',    'Kulkarni',  'tanvi.kulkarni@university.edu',  '2024-01-08', 'Physics');

-- Courses (6 courses)
INSERT INTO courses (course_name, credits, instructor) VALUES
  ('Introduction to Programming',   4, 'Dr. Sunil Rao'),
  ('Data Structures & Algorithms',  4, 'Dr. Sunil Rao'),
  ('Calculus I',                     3, 'Prof. Anita Deshmukh'),
  ('English Composition',           3, 'Dr. Karen Thomas'),
  ('Physics Mechanics',             4, 'Prof. V. Krishnamurthy'),
  ('Database Systems',              3, 'Dr. Meena Iyer');

-- Enrollments (18 enrollments with varied grades)
INSERT INTO enrollments (student_id, course_id, semester, grade) VALUES
  -- Aditya Bhatt - strong CS student
  (1, 1, 'Fall 2022',   'A'),
  (1, 3, 'Fall 2022',   'B'),
  (1, 2, 'Spring 2023', 'A'),
  (1, 6, 'Fall 2023',   'A'),
  -- Fatima Khan - math major
  (2, 3, 'Fall 2022',   'A'),
  (2, 1, 'Fall 2022',   'B'),
  (2, 4, 'Spring 2023', 'A'),
  -- Rahul Menon
  (3, 1, 'Spring 2023', 'C'),
  (3, 2, 'Fall 2023',   'B'),
  (3, 6, 'Fall 2023',   'B'),
  -- Ishita Das - physics major
  (4, 5, 'Spring 2023', 'A'),
  (4, 3, 'Spring 2023', 'A'),
  -- Siddharth Jain
  (5, 1, 'Fall 2023',   'B'),
  (5, 2, 'Spring 2024', 'C'),
  -- Nisha Agarwal
  (6, 4, 'Fall 2023',   'A'),
  (6, 1, 'Fall 2023',   'D'),
  -- Omar Sheikh
  (7, 3, 'Spring 2024', 'B'),
  -- Tanvi Kulkarni - no grades yet (just enrolled)
  (8, 5, 'Spring 2024', NULL);
