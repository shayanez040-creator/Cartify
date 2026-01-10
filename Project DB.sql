-- =========================
-- RESET DATABASE
-- =========================
DROP DATABASE IF EXISTS ecommerce;
CREATE DATABASE ecommerce;
USE ecommerce;

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,   -- ❗ unique username
    password VARCHAR(255) NOT NULL,
    role ENUM('ADMIN', 'CUSTOMER') NOT NULL
);

-- =========================
-- INSERT ONE FIXED ADMIN
-- =========================
INSERT INTO users (username, password, role)
VALUES ('admin', 'admin123', 'ADMIN'), ('Hassan', 'Shayan', 'CUSTOMER');

-- =========================
-- CUSTOMERS TABLE
-- =========================
CREATE TABLE customers (
    customer_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT UNIQUE NOT NULL,
    name VARCHAR(100),
    email VARCHAR(100),
    phone VARCHAR(20),
    address VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- =========================
-- CATEGORY TABLE
-- =========================
CREATE TABLE category (
    category_id INT AUTO_INCREMENT PRIMARY KEY,
    category_name VARCHAR(100) NOT NULL UNIQUE
);

INSERT INTO category (category_name) VALUES
('Electronics'),
('Clothing'),
('Beauty'),
('Sports'),
('Home Decor'),
('Toys'),
('Footwear'),
('Wearables'),
('Gaming'),
('Audio'),
('Accessories');

-- =========================
-- PRODUCTS TABLE
-- =========================
CREATE TABLE products (
    product_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    description TEXT,
    price DECIMAL(10,2),
    stock INT,
    image VARCHAR(255),
    category_id INT,
    FOREIGN KEY (category_id) REFERENCES category(category_id)
);

INSERT INTO products (name, description, price, stock, image, category_id) VALUES
('iPhone 14', 'Latest Apple smartphone', 350000, 20, 'iphone14.jpg', 1),
('Mens T-Shirt', '100% cotton shirt', 1500, 100, 'tshirt.jpg', 2),
('Lipstick Set', '12 color lipstick kit', 1500, 50, 'lipstick.jpg', 3),
('Football', 'Standard size football', 2500, 30, 'football.jpg', 4),
('Home Vase Decor', 'Ceramic decorative vase', 3000, 25, 'vase.jpg', 5),
('Teddy Bear Large', 'Soft teddy bear', 3000, 15, 'teddy.jpg', 6),
('Wireless Headphones', 'Noise cancelling headphones', 8500, 40, 'headphones.jpg', 1),
('Running Shoes', 'Comfortable sports shoes', 12000, 35, 'shoes.jpg', 7),
('Smart Watch', 'Fitness tracking smartwatch', 22000, 25, 'watch.jpg', 8),
('Gaming Mouse', 'RGB gaming mouse', 4500, 60, 'mouse.jpg', 9),
('Bluetooth Speaker', 'Portable bluetooth speaker', 9800, 45, 'speaker.jpg', 10),
('Leather Wallet', 'Genuine leather wallet', 3200, 70, 'wallet.jpg', 11);

-- =========================
-- CART TABLE
-- =========================
CREATE TABLE cart (
    cart_id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT,
    product_id INT,
    quantity INT DEFAULT 1,
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(product_id) ON DELETE CASCADE
);

-- =========================
-- ORDERS TABLE
-- =========================
CREATE TABLE orders (
    order_id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT,
    order_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    total_amount DECIMAL(10,2),
    status VARCHAR(50) DEFAULT 'Pending',
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id)
);

-- =========================
-- ORDER ITEMS TABLE
-- =========================
CREATE TABLE order_items (
    order_item_id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT,
    product_id INT,
    quantity INT,
    price DECIMAL(10,2),
    FOREIGN KEY (order_id) REFERENCES orders(order_id),
    FOREIGN KEY (product_id) REFERENCES products(product_id)
);

select * from users;
