CREATE TABLE products (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    brand VARCHAR(255) NOT NULL,
    price INT NOT NULL,
    category_id INT,
    status_stock ENUM('OUT_OF_STOCK', 'IN_STOCK') NOT NULL,
    FOREIGN KEY (category_id) REFERENCES categories(id)
);
