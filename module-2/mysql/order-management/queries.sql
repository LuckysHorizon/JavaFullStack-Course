-- ============================================
-- Order Management System - Practice Queries
-- 15+ queries  revenue, rankings, analytics
-- ============================================

USE order_management;

-- -------------------------------------------
-- 1. Monthly revenue report
--    (only non-cancelled orders)
-- -------------------------------------------
SELECT
  DATE_FORMAT(o.order_date, '%Y-%m') AS month,
  COUNT(DISTINCT o.order_id)         AS total_orders,
  SUM(o.total_amount)                AS revenue
FROM orders o
WHERE o.status <> 'cancelled'
GROUP BY DATE_FORMAT(o.order_date, '%Y-%m')
ORDER BY month;

-- -------------------------------------------
-- 2. Top 5 customers by total spending
-- -------------------------------------------
SELECT
  c.name,
  c.city,
  COUNT(o.order_id)    AS orders_placed,
  SUM(o.total_amount)  AS total_spent
FROM customers c
INNER JOIN orders o ON c.customer_id = o.customer_id
WHERE o.status <> 'cancelled'
GROUP BY c.customer_id, c.name, c.city
ORDER BY total_spent DESC
LIMIT 5;

-- -------------------------------------------
-- 3. Best-selling products by quantity sold
-- -------------------------------------------
SELECT
  p.product_name,
  p.category,
  SUM(oi.quantity) AS total_qty_sold,
  SUM(oi.quantity * oi.unit_price) AS total_revenue
FROM products p
INNER JOIN order_items oi ON p.product_id = oi.product_id
INNER JOIN orders o       ON oi.order_id = o.order_id
WHERE o.status <> 'cancelled'
GROUP BY p.product_id, p.product_name, p.category
ORDER BY total_qty_sold DESC;

-- -------------------------------------------
-- 4. Orders with multiple items
-- -------------------------------------------
SELECT
  o.order_id,
  c.name AS customer,
  o.order_date,
  COUNT(oi.item_id)  AS item_count,
  o.total_amount
FROM orders o
INNER JOIN customers c   ON o.customer_id = c.customer_id
INNER JOIN order_items oi ON o.order_id = oi.order_id
GROUP BY o.order_id, c.name, o.order_date, o.total_amount
HAVING item_count > 1
ORDER BY item_count DESC;

-- -------------------------------------------
-- 5. Products never ordered
-- -------------------------------------------
SELECT p.product_id, p.product_name, p.category, p.stock_quantity
FROM products p
LEFT JOIN order_items oi ON p.product_id = oi.product_id
WHERE oi.item_id IS NULL;

-- Alternative with NOT EXISTS:
-- SELECT p.product_name FROM products p
-- WHERE NOT EXISTS (
--   SELECT 1 FROM order_items oi WHERE oi.product_id = p.product_id
-- );

-- -------------------------------------------
-- 6. Average order value (excluding cancelled)
-- -------------------------------------------
SELECT
  ROUND(AVG(total_amount), 2) AS avg_order_value,
  MIN(total_amount) AS min_order,
  MAX(total_amount) AS max_order
FROM orders
WHERE status <> 'cancelled';

-- -------------------------------------------
-- 7. Customer purchase frequency
-- -------------------------------------------
SELECT
  c.name,
  COUNT(o.order_id) AS order_count,
  MIN(o.order_date) AS first_order,
  MAX(o.order_date) AS last_order,
  DATEDIFF(MAX(o.order_date), MIN(o.order_date)) AS days_between_first_last
FROM customers c
INNER JOIN orders o ON c.customer_id = o.customer_id
WHERE o.status <> 'cancelled'
GROUP BY c.customer_id, c.name
ORDER BY order_count DESC;

-- -------------------------------------------
-- 8. Category-wise revenue
-- -------------------------------------------
SELECT
  p.category,
  COUNT(DISTINCT oi.order_id)        AS orders_containing,
  SUM(oi.quantity)                    AS total_units,
  SUM(oi.quantity * oi.unit_price)    AS category_revenue
FROM products p
INNER JOIN order_items oi ON p.product_id = oi.product_id
INNER JOIN orders o       ON oi.order_id = o.order_id
WHERE o.status <> 'cancelled'
GROUP BY p.category
ORDER BY category_revenue DESC;

-- -------------------------------------------
-- 9. Pending orders older than 7 days
--    (using CURDATE for dynamic comparison)
-- -------------------------------------------
SELECT
  o.order_id,
  c.name AS customer,
  o.order_date,
  DATEDIFF(CURDATE(), o.order_date) AS days_pending,
  o.total_amount
FROM orders o
INNER JOIN customers c ON o.customer_id = c.customer_id
WHERE o.status = 'pending'
  AND DATEDIFF(CURDATE(), o.order_date) > 7
ORDER BY o.order_date;

-- -------------------------------------------
-- 10. Payment method breakdown
-- -------------------------------------------
SELECT
  p.method,
  COUNT(*)             AS transaction_count,
  SUM(p.amount)        AS total_collected,
  ROUND(AVG(p.amount), 2) AS avg_payment
FROM payments p
GROUP BY p.method
ORDER BY total_collected DESC;

-- -------------------------------------------
-- 11. Complex nested subquery  customers
--     who spent more than the overall average
--     customer spending
-- -------------------------------------------
SELECT c.name, c.city, customer_totals.total_spent
FROM customers c
INNER JOIN (
  SELECT customer_id, SUM(total_amount) AS total_spent
  FROM orders
  WHERE status <> 'cancelled'
  GROUP BY customer_id
) AS customer_totals ON c.customer_id = customer_totals.customer_id
WHERE customer_totals.total_spent > (
  SELECT AVG(cust_total)
  FROM (
    SELECT SUM(total_amount) AS cust_total
    FROM orders
    WHERE status <> 'cancelled'
    GROUP BY customer_id
  ) AS avg_table
)
ORDER BY customer_totals.total_spent DESC;

-- -------------------------------------------
-- 12. Order details  full breakdown with
--     all line items
-- -------------------------------------------
SELECT
  o.order_id,
  c.name                                  AS customer,
  p.product_name,
  oi.quantity,
  oi.unit_price,
  (oi.quantity * oi.unit_price)            AS line_total,
  o.status
FROM orders o
INNER JOIN customers c    ON o.customer_id = c.customer_id
INNER JOIN order_items oi ON o.order_id = oi.order_id
INNER JOIN products p     ON oi.product_id = p.product_id
ORDER BY o.order_id, p.product_name;

-- -------------------------------------------
-- 13. Revenue by city
-- -------------------------------------------
SELECT
  c.city,
  COUNT(DISTINCT c.customer_id) AS customers,
  COUNT(DISTINCT o.order_id)    AS orders,
  SUM(o.total_amount)           AS city_revenue
FROM customers c
INNER JOIN orders o ON c.customer_id = o.customer_id
WHERE o.status <> 'cancelled'
GROUP BY c.city
ORDER BY city_revenue DESC;

-- -------------------------------------------
-- 14. Products with low stock (below 100)
--     that are also popular (ordered 2+ times)
-- -------------------------------------------
SELECT
  p.product_name,
  p.stock_quantity,
  SUM(oi.quantity) AS total_ordered
FROM products p
INNER JOIN order_items oi ON p.product_id = oi.product_id
WHERE p.stock_quantity < 100
GROUP BY p.product_id, p.product_name, p.stock_quantity
HAVING total_ordered >= 2
ORDER BY p.stock_quantity ASC;

-- -------------------------------------------
-- 15. Unpaid orders  orders without a
--     matching payment record
-- -------------------------------------------
SELECT
  o.order_id,
  c.name         AS customer,
  o.order_date,
  o.total_amount,
  o.status
FROM orders o
INNER JOIN customers c ON o.customer_id = c.customer_id
LEFT JOIN payments pay ON o.order_id = pay.order_id
WHERE pay.payment_id IS NULL
  AND o.status <> 'cancelled'
ORDER BY o.order_date;

-- -------------------------------------------
-- 16. Month-over-month revenue growth
--     (window function, MySQL 8.0+)
-- -------------------------------------------
SELECT
  month,
  revenue,
  LAG(revenue) OVER (ORDER BY month) AS prev_month_revenue,
  ROUND(
    (revenue - LAG(revenue) OVER (ORDER BY month))
    / LAG(revenue) OVER (ORDER BY month) * 100, 1
  ) AS growth_pct
FROM (
  SELECT
    DATE_FORMAT(order_date, '%Y-%m') AS month,
    SUM(total_amount)                AS revenue
  FROM orders
  WHERE status <> 'cancelled'
  GROUP BY DATE_FORMAT(order_date, '%Y-%m')
) AS monthly
ORDER BY month;

-- -------------------------------------------
-- 17. Top product per category (using
--     window function)
-- -------------------------------------------
SELECT category, product_name, total_revenue
FROM (
  SELECT
    p.category,
    p.product_name,
    SUM(oi.quantity * oi.unit_price) AS total_revenue,
    ROW_NUMBER() OVER (PARTITION BY p.category ORDER BY SUM(oi.quantity * oi.unit_price) DESC) AS rn
  FROM products p
  INNER JOIN order_items oi ON p.product_id = oi.product_id
  INNER JOIN orders o       ON oi.order_id = o.order_id
  WHERE o.status <> 'cancelled'
  GROUP BY p.category, p.product_name
) ranked
WHERE rn = 1
ORDER BY total_revenue DESC;

-- -------------------------------------------
-- 18. Customer segmentation by spending
-- -------------------------------------------
SELECT
  c.name,
  SUM(o.total_amount) AS total_spent,
  CASE
    WHEN SUM(o.total_amount) >= 15000 THEN 'Premium'
    WHEN SUM(o.total_amount) >= 5000  THEN 'Regular'
    ELSE 'Occasional'
  END AS customer_tier
FROM customers c
INNER JOIN orders o ON c.customer_id = o.customer_id
WHERE o.status <> 'cancelled'
GROUP BY c.customer_id, c.name
ORDER BY total_spent DESC;
