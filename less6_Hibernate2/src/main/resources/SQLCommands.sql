BEGIN;

DROP TABLE IF EXISTS products CASCADE;
CREATE TABLE products (
    id bigserial PRIMARY KEY,
    title VARCHAR(128),
    price INTEGER
);
INSERT INTO products (title, price) VALUES
('TV', 999),
('Xbox', 299),
('PlayStation', 399);

DROP TABLE IF EXISTS customers CASCADE;
CREATE TABLE customers (
    id bigserial PRIMARY KEY,
    name VARCHAR(128)
);
INSERT INTO customers (name) VALUES
('Alexey'),
('Vlad'),
('Dmitriy');

DROP TABLE IF EXISTS orders CASCADE;
CREATE TABLE orders (
    customer_id bigint,
    product_id bigint,
    FOREIGN KEY (customer_id) REFERENCES customers(id),
    FOREIGN KEY (product_id) REFERENCES products(id)
);
INSERT INTO orders VALUES
(1, 1),
(1, 2),
(2, 3),
(3, 3);

COMMIT;