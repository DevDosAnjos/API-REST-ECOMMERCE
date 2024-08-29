CREATE TABLE purchases (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    order_id INT,
    created_at DATETIME NOT NULL,
    total INT NOT NULL,
    delivery_address VARCHAR(255) NOT NULL,
    payment_method ENUM('CREDIT_CARD', 'DEBIT_CARD', 'PIX') NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (order_id) REFERENCES orders(id)
);
