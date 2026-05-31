-- ============================================
-- Student Database - Practice Queries
-- 15+ queries with joins, aggregates, GPA
-- ============================================

USE student_database;

-- -------------------------------------------
-- 1. All students with their major, ordered
--    by enrollment date
-- -------------------------------------------
SELECT student_id, first_name, last_name, major, enrollment_date
FROM students
ORDER BY enrollment_date, last_name;

-- -------------------------------------------
-- 2. Students enrolled in 'Introduction to
--    Programming'
-- -------------------------------------------
SELECT s.first_name, s.last_name, e.semester, e.grade
FROM students s
INNER JOIN enrollments e ON s.student_id = e.student_id
INNER JOIN courses c     ON e.course_id = c.course_id
WHERE c.course_name = 'Introduction to Programming'
ORDER BY e.semester;

-- -------------------------------------------
-- 3. GPA calculation per student
--    Grade scale: A=4, B=3, C=2, D=1, F=0
--    GPA = SUM(grade_points * credits) / SUM(credits)
-- -------------------------------------------
SELECT
  s.first_name,
  s.last_name,
  ROUND(
    SUM(
      CASE e.grade
        WHEN 'A' THEN 4
        WHEN 'B' THEN 3
        WHEN 'C' THEN 2
        WHEN 'D' THEN 1
        WHEN 'F' THEN 0
        ELSE NULL
      END * c.credits
    ) / SUM(
      CASE WHEN e.grade IS NOT NULL THEN c.credits ELSE 0 END
    ), 2
  ) AS gpa,
  SUM(CASE WHEN e.grade IS NOT NULL THEN c.credits ELSE 0 END) AS total_credits
FROM students s
INNER JOIN enrollments e ON s.student_id = e.student_id
INNER JOIN courses c     ON e.course_id = c.course_id
GROUP BY s.student_id, s.first_name, s.last_name
HAVING total_credits > 0
ORDER BY gpa DESC;

-- -------------------------------------------
-- 4. Courses ranked by enrollment count
-- -------------------------------------------
SELECT c.course_name, c.instructor, COUNT(e.enrollment_id) AS enrollment_count
FROM courses c
LEFT JOIN enrollments e ON c.course_id = e.course_id
GROUP BY c.course_id, c.course_name, c.instructor
ORDER BY enrollment_count DESC;

-- -------------------------------------------
-- 5. Honor Roll  students with ALL A grades
--    (and at least one graded course)
-- -------------------------------------------
SELECT s.first_name, s.last_name, s.major
FROM students s
WHERE s.student_id IN (
  SELECT e.student_id
  FROM enrollments e
  WHERE e.grade IS NOT NULL
  GROUP BY e.student_id
  HAVING COUNT(*) = SUM(CASE WHEN e.grade = 'A' THEN 1 ELSE 0 END)
);
-- Could also use NOT EXISTS to check no non-A grade exists

-- -------------------------------------------
-- 6. Average grade point per course
-- -------------------------------------------
SELECT
  c.course_name,
  COUNT(e.enrollment_id) AS students,
  ROUND(AVG(
    CASE e.grade
      WHEN 'A' THEN 4
      WHEN 'B' THEN 3
      WHEN 'C' THEN 2
      WHEN 'D' THEN 1
      WHEN 'F' THEN 0
      ELSE NULL
    END
  ), 2) AS avg_grade_point
FROM courses c
INNER JOIN enrollments e ON c.course_id = e.course_id
WHERE e.grade IS NOT NULL
GROUP BY c.course_id, c.course_name
ORDER BY avg_grade_point DESC;

-- -------------------------------------------
-- 7. Students NOT enrolled in any course
-- -------------------------------------------
SELECT s.first_name, s.last_name, s.email
FROM students s
LEFT JOIN enrollments e ON s.student_id = e.student_id
WHERE e.enrollment_id IS NULL;

-- Alternative using NOT EXISTS:
-- SELECT s.first_name, s.last_name
-- FROM students s
-- WHERE NOT EXISTS (
--   SELECT 1 FROM enrollments e
--   WHERE e.student_id = s.student_id
-- );

-- -------------------------------------------
-- 8. Total credits completed per student
--    (only graded enrollments count)
-- -------------------------------------------
SELECT
  s.first_name,
  s.last_name,
  COALESCE(SUM(
    CASE WHEN e.grade IS NOT NULL AND e.grade <> 'F'
         THEN c.credits ELSE 0 END
  ), 0) AS credits_completed
FROM students s
LEFT JOIN enrollments e ON s.student_id = e.student_id
LEFT JOIN courses c     ON e.course_id = c.course_id
GROUP BY s.student_id, s.first_name, s.last_name
ORDER BY credits_completed DESC;

-- -------------------------------------------
-- 9. Courses that a specific student has NOT
--    taken (e.g., student_id = 1)
-- -------------------------------------------
SELECT c.course_name, c.credits
FROM courses c
WHERE c.course_id NOT IN (
  SELECT e.course_id
  FROM enrollments e
  WHERE e.student_id = 1
);

-- -------------------------------------------
-- 10. Enrollment distribution by semester
-- -------------------------------------------
SELECT semester, COUNT(*) AS total_enrollments
FROM enrollments
GROUP BY semester
ORDER BY semester;

-- -------------------------------------------
-- 11. Students taking the most courses
-- -------------------------------------------
SELECT
  s.first_name,
  s.last_name,
  COUNT(e.course_id) AS courses_taken
FROM students s
INNER JOIN enrollments e ON s.student_id = e.student_id
GROUP BY s.student_id, s.first_name, s.last_name
ORDER BY courses_taken DESC
LIMIT 3;

-- -------------------------------------------
-- 12. Grade distribution across all courses
-- -------------------------------------------
SELECT
  e.grade,
  COUNT(*) AS count,
  ROUND(COUNT(*) * 100.0 / (SELECT COUNT(*) FROM enrollments WHERE grade IS NOT NULL), 1) AS percentage
FROM enrollments e
WHERE e.grade IS NOT NULL
GROUP BY e.grade
ORDER BY
  CASE e.grade
    WHEN 'A' THEN 1
    WHEN 'B' THEN 2
    WHEN 'C' THEN 3
    WHEN 'D' THEN 4
    WHEN 'F' THEN 5
  END;

-- -------------------------------------------
-- 13. Multi-table join  full enrollment
--     details
-- -------------------------------------------
SELECT
  CONCAT(s.first_name, ' ', s.last_name) AS student,
  s.major,
  c.course_name,
  c.credits,
  c.instructor,
  e.semester,
  COALESCE(e.grade, 'In Progress') AS grade
FROM enrollments e
INNER JOIN students s ON e.student_id = s.student_id
INNER JOIN courses c  ON e.course_id = c.course_id
ORDER BY s.last_name, e.semester;

-- -------------------------------------------
-- 14. Students who improved  got a higher
--     grade in a later semester vs earlier
--     (comparing same student across semesters)
-- -------------------------------------------
SELECT DISTINCT
  CONCAT(s.first_name, ' ', s.last_name) AS student,
  e1.semester AS earlier_semester,
  e1.grade    AS earlier_grade,
  e2.semester AS later_semester,
  e2.grade    AS later_grade
FROM enrollments e1
INNER JOIN enrollments e2 ON e1.student_id = e2.student_id
  AND e1.semester < e2.semester
INNER JOIN students s ON e1.student_id = s.student_id
WHERE
  CASE e2.grade WHEN 'A' THEN 4 WHEN 'B' THEN 3 WHEN 'C' THEN 2 WHEN 'D' THEN 1 ELSE 0 END
  >
  CASE e1.grade WHEN 'A' THEN 4 WHEN 'B' THEN 3 WHEN 'C' THEN 2 WHEN 'D' THEN 1 ELSE 0 END
ORDER BY student;

-- -------------------------------------------
-- 15. Computer Science majors  courses and
--     grades overview
-- -------------------------------------------
SELECT
  CONCAT(s.first_name, ' ', s.last_name) AS student,
  c.course_name,
  e.grade
FROM students s
INNER JOIN enrollments e ON s.student_id = e.student_id
INNER JOIN courses c     ON e.course_id = c.course_id
WHERE s.major = 'Computer Science'
ORDER BY s.last_name, c.course_name;

-- -------------------------------------------
-- 16. Instructors ranked by average student
--     grade point
-- -------------------------------------------
SELECT
  c.instructor,
  COUNT(DISTINCT c.course_id)   AS courses_taught,
  COUNT(e.enrollment_id)        AS total_students,
  ROUND(AVG(
    CASE e.grade
      WHEN 'A' THEN 4 WHEN 'B' THEN 3 WHEN 'C' THEN 2
      WHEN 'D' THEN 1 WHEN 'F' THEN 0 ELSE NULL
    END
  ), 2) AS avg_student_gpa
FROM courses c
INNER JOIN enrollments e ON c.course_id = e.course_id
WHERE e.grade IS NOT NULL
GROUP BY c.instructor
ORDER BY avg_student_gpa DESC;
