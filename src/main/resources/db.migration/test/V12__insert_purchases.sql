INSERT INTO purchases (user_id, order_id, created_at, total, delivery_address, payment_method)
VALUES (1, 1, NOW(), 6000, '123 Test Street', 'CREDIT_CARD');

INSERT INTO purchases (user_id, order_id, created_at, total, delivery_address, payment_method)
VALUES (2, 2, NOW(), 50, '456 Test Avenue', 'DEBIT_CARD');

INSERT INTO purchases (user_id, order_id, created_at, total, delivery_address, payment_method)
VALUES (3, 3, NOW(), 200, '789 Test Blvd', 'PIX');

INSERT INTO purchases (user_id, order_id, created_at, total, delivery_address, payment_method)
VALUES (4, 4, NOW(), 9000, '101 Test Lane', 'CREDIT_CARD');
