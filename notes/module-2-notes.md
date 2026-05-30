# Module 2  MySQL Notes

## SQL Basics
- DDL (CREATE, ALTER, DROP) changes structure, DML (INSERT, UPDATE, DELETE) changes data
- Always define PRIMARY KEY  auto-increment with AUTO_INCREMENT in MySQL
- Use NOT NULL and DEFAULT constraints to enforce data integrity
- Index frequently queried columns, but don't over-index (slows down writes)

## Joins
- INNER JOIN = only matching rows from both tables
- LEFT JOIN = all rows from left + matching from right (NULLs where no match)
- Self-join is useful for hierarchical data (e.g., employee-manager relationships)

## Aggregates
- GROUP BY groups rows, aggregate functions operate per group
- HAVING filters groups (like WHERE but for aggregated data)
- Common pattern: SELECT dept, COUNT(*) FROM employees GROUP BY dept HAVING COUNT(*) > 5

## Subqueries
- Correlated subquery runs once per outer row  can be slow on large tables
- IN with subquery for filtering, scalar subquery for computed columns
- EXISTS is often faster than IN for large datasets

## Best Practices
- Uppercase SQL keywords for readability
- Use meaningful table/column names (not t1, col1)
- Add comments for complex queries
- Always test with sample data before running on production
