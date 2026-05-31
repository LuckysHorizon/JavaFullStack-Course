-- ============================================
-- Order Management System - Schema & Data
-- Database: order_management
-- ============================================

DROP DATABASE IF EXISTS order_management;
CREATE DATABASE order_management;
USE order_management;

-- -------------------------------------------
-- Table: customers
-- -------------------------------------------
CREATE TABLE customers (
  customer_id       INT           AUTO_INCREMENT,
  name              VARCHAR(100)  NOT NULL,
  email             VARCHAR(100)  NOT NULL UNIQUE,
  city              VARCHAR(100),
  registration_date DATE          NOT NULL,
  PRIMARY KEY (customer_id)
);

-- -------------------------------------------
-- Table: products
-- -------------------------------------------
CREATE TABLE products (
  product_id      INT             AUTO_INCREMENT,
  product_name    VARCHAR(150)    NOT NULL,
  category        VARCHAR(50)     NOT NULL,
  price           DECIMAL(10, 2)  NOT NULL CHECK (price > 0),
  stock_quantity  INT             NOT NULL DEFAULT 0,
  PRIMARY KEY (product_id)
);

-- -------------------------------------------
-- Table: orders
-- -------------------------------------------
CREATE TABLE orders (
  order_id      INT             AUTO_INCREMENT,
  customer_id   INT             NOT NULL,
  order_date    DATE            NOT NULL,
  total_amount  DECIMAL(10, 2)  DEFAULT 0.00,
  status        ENUM('pending', 'shipped', 'delivered', 'cancelled') DEFAULT 'pending',
  PRIMARY KEY (order_id),
  FOREIGN KEY (customer_id) REFERENCES customers(customer_id)
    ON DELETE CASCADE ON UPDATE CASCADE
);

-- -------------------------------------------
-- Table: order_items
-- Individual line items within an order
-- -------------------------------------------
CREATE TABLE order_items (
  item_id     INT             AUTO_INCREMENT,
  order_id    INT             NOT NULL,
  product_id  INT             NOT NULL,
  quantity    INT             NOT NULL CHECK (quantity > 0),
  unit_price  DECIMAL(10, 2)  NOT NULL,
  PRIMARY KEY (item_id),
  FOREIGN KEY (order_id)   REFERENCES orders(order_id)
    ON DELETE CASCADE ON UPDATE CASCADE,
  FOREIGN KEY (product_id) REFERENCES products(product_id)
    ON DELETE RESTRICT ON UPDATE CASCADE
);

-- -------------------------------------------
-- Table: payments
-- -------------------------------------------
CREATE TABLE payments (
  payment_id    INT             AUTO_INCREMENT,
  order_id      INT             NOT NULL,
  payment_date  DATE            NOT NULL,
  amount        DECIMAL(10, 2)  NOT NULL,
  method        ENUM('credit_card', 'debit_card', 'upi', 'cash') NOT NULL,
  PRIMARY KEY (payment_id),
  FOREIGN KEY (order_id) REFERENCES orders(order_id)
    ON DELETE CASCADE ON UPDATE CASCADE
);

-- Indexes
CREATE INDEX idx_order_customer  ON orders(customer_id);
CREATE INDEX idx_order_date      ON orders(order_date);
CREATE INDEX idx_oi_order        ON order_items(order_id);
CREATE INDEX idx_oi_product      ON order_items(product_id);
CREATE INDEX idx_product_cat     ON products(category);

-- ============================================
-- Sample Data
-- ============================================

-- Customers (8)
INSERT INTO customers (name, email, city, registration_date) VALUES
  ('Arjun Malhotra',  'arjun.m@email.com',     'Mumbai',     '2022-03-10'),
  ('Divya Nambiar',   'divya.n@email.com',      'Bangalore',  '2022-05-22'),
  ('Farhan Qureshi',  'farhan.q@email.com',     'Delhi',      '2022-08-15'),
  ('Geeta Pillai',    'geeta.p@email.com',      'Chennai',    '2023-01-05'),
  ('Harsh Tiwari',    'harsh.t@email.com',      'Pune',       '2023-04-18'),
  ('Isha Saxena',     'isha.s@email.com',       'Hyderabad',  '2023-06-30'),
  ('Jayant Bose',     'jayant.b@email.com',     'Kolkata',    '2023-09-12'),
  ('Kavya Rajan',     'kavya.r@email.com',      'Mumbai',     '2024-01-20');

-- Products (10)
INSERT INTO products (product_name, category, price, stock_quantity) VALUES
  ('Wireless Bluetooth Headphones',  'Electronics',  2499.00,  150),
  ('USB-C Hub Adapter',              'Electronics',  1899.00,  200),
  ('Ergonomic Office Chair',         'Furniture',    15999.00, 40),
  ('Mechanical Keyboard',            'Electronics',  3499.00,  120),
  ('LED Desk Lamp',                  'Furniture',    1299.00,  85),
  ('Python Programming Book',        'Books',        599.00,   300),
  ('Java Complete Reference',        'Books',        749.00,   250),
  ('Laptop Stand - Aluminum',        'Accessories',  2199.00,  95),
  ('Webcam HD 1080p',                'Electronics',  2999.00,  70),
  ('Notebook - Ruled 200 Pages',     'Stationery',   149.00,   500);

-- Orders (12)
INSERT INTO orders (customer_id, order_date, total_amount, status) VALUES
  (1, '2024-01-15', 6497.00,  'delivered'),
  (2, '2024-01-20', 15999.00, 'delivered'),
  (1, '2024-02-10', 1348.00,  'delivered'),
  (3, '2024-02-28', 5998.00,  'shipped'),
  (4, '2024-03-05', 2499.00,  'delivered'),
  (5, '2024-03-15', 4698.00,  'shipped'),
  (2, '2024-04-01', 3499.00,  'pending'),
  (6, '2024-04-10', 749.00,   'delivered'),
  (3, '2024-04-20', 2999.00,  'pending'),
  (7, '2024-05-01', 17498.00, 'pending'),
  (1, '2024-05-10', 599.00,   'cancelled'),
  (8, '2024-05-15', 4398.00,  'pending');

-- Order Items (22 items across the 12 orders)
INSERT INTO order_items (order_id, product_id, quantity, unit_price) VALUES
  -- Order 1: Arjun bought headphones + keyboard
  (1,  1, 1, 2499.00),
  (1,  4, 1, 3499.00),
  (1, 10, 3, 149.00),
  -- Order 2: Divya bought office chair
  (2,  3, 1, 15999.00),
  -- Order 3: Arjun bought books
  (3,  6, 1, 599.00),
  (3,  7, 1, 749.00),
  -- Order 4: Farhan bought 2 USB-C hubs
  (4,  2, 2, 1899.00),
  (4,  8, 1, 2199.00),
  -- Order 5: Geeta bought headphones
  (5,  1, 1, 2499.00),
  -- Order 6: Harsh bought laptop stand + headphones
  (6,  8, 1, 2199.00),
  (6,  1, 1, 2499.00),
  -- Order 7: Divya bought keyboard
  (7,  4, 1, 3499.00),
  -- Order 8: Isha bought Java book
  (8,  7, 1, 749.00),
  -- Order 9: Farhan bought webcam
  (9,  9, 1, 2999.00),
  -- Order 10: Jayant bought chair + desk lamp
  (10, 3, 1, 15999.00),
  (10, 5, 1, 1299.00),
  (10, 10, 2, 149.00),
  -- Order 11: Arjun - cancelled order
  (11, 6, 1, 599.00),
  -- Order 12: Kavya bought USB-C hub + laptop stand
  (12, 2, 1, 1899.00),
  (12, 8, 1, 2199.00),
  (12, 10, 2, 149.00);

-- Payments (10  some orders haven't been paid yet)
INSERT INTO payments (order_id, payment_date, amount, method) VALUES
  (1,  '2024-01-15', 6497.00,  'credit_card'),
  (2,  '2024-01-20', 15999.00, 'debit_card'),
  (3,  '2024-02-10', 1348.00,  'upi'),
  (4,  '2024-02-28', 5998.00,  'credit_card'),
  (5,  '2024-03-05', 2499.00,  'upi'),
  (6,  '2024-03-15', 4698.00,  'debit_card'),
  (7,  '2024-04-01', 3499.00,  'credit_card'),
  (8,  '2024-04-10', 749.00,   'cash'),
  (10, '2024-05-01', 17498.00, 'credit_card'),
  (12, '2024-05-15', 4398.00,  'upi');
