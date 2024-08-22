CREATE TABLE purchases (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    order_id INT,
    createdAt DATETIME NOT NULL,
    total INT NOT NULL,
    deliveryAddress VARCHAR(255) NOT NULL,
    paymentMethod ENUM('CREDIT_CARD', 'DEBIT_CARD', 'PIX') NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (order_id) REFERENCES orders(id)
);
