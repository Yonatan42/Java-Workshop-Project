USE java_workshop_db;


DROP TRIGGER trigger_orders_update;
DROP TABLE orders;

/*
CREATE TABLE orders (
	id INT NOT NULL AUTO_INCREMENT,
	customer_id INT NOT NULL,
    total_cost DECIMAL(10,2) NOT NULL,
    phone VARCHAR(20) NOT NULL, # may be different from the one registered for the customer
    address TEXT NOT NULL, # may be different from the one registered for the customer
	created TIMESTAMP DEFAULT NOW(),
	modified TIMESTAMP DEFAULT NOW(),
    FOREIGN KEY (customer_id) REFERENCES customers(id) ON UPDATE CASCADE ON DELETE RESTRICT,
	PRIMARY KEY (id)
);

ALTER TABLE orders DROP total_cost;

ALTER TABLE orders CHANGE customer_id user_id INT NOT NULL;

ALTER TABLE orders ADD email VARCHAR(320) NOT NULL AFTER user_id;

ALTER TABLE orders ADD first_name VARCHAR(32) NOT NULL AFTER email;
ALTER TABLE orders ADD last_name VARCHAR(32) NOT NULL AFTER first_name;
*/

CREATE TABLE orders (
	id INT NOT NULL AUTO_INCREMENT,
	user_id INT NOT NULL,
    email VARCHAR(320) NOT NULL, # may be different from the one registered for the user
	first_name VARCHAR(32) NOT NULL,  # may be different from the one registered for the user
	last_name VARCHAR(32) NOT NULL,  # may be different from the one registered for the user
    phone VARCHAR(20) NOT NULL, # may be different from the one registered for the user
    address TEXT NOT NULL, # may be different from the one registered for the user
	created TIMESTAMP DEFAULT NOW(),
	modified TIMESTAMP DEFAULT NOW(),
    FOREIGN KEY (customer_id) REFERENCES customers(id) ON UPDATE CASCADE ON DELETE RESTRICT,
	PRIMARY KEY (id)
);



 
CREATE TRIGGER trigger_orders_update
BEFORE UPDATE ON orders
FOR EACH ROW
SET NEW.modified = NOW();

# test category
INSERT INTO orders (customer_id, total_cost, phone, address) VALUES (2, 185.42, '0500532446', '32 North Plane Rd. Silver Panes, Netherworld 458536');


#test update
# UPDATE orders  SET name = 'cat2' WHERE id = 1;

# delete all from table
DELETE FROM orders WHERE id > 0;


# will have paging - the LIMIT can be used (if it gets 2 values then the first is the number to start AFTER and the second is the amount)


SELECT * FROM orders;




-- ordera details

DROP VIEW orders_details;


CREATE VIEW orders_details AS
SELECT 
o.id AS order_id, 
u.id AS user_id,
p.id AS product_id,
CONCAT(u.last_name,  ' , ', u.first_name) AS full_name,
u.email,
o.phone,
o.address,
p.title AS product_name,
op.price_at_order AS price,
op.quantity,
op.price_at_order * op.quantity AS total_price,
o.created AS transaction_date
FROM orders AS o 
INNER JOIN users AS u ON o.customer_id = u.id 
INNER JOIN order_products AS op ON o.id = op.order_id
INNER JOIN products as p ON op.product_id = p.id;



SELECT * FROM orders_details;


-- orders summaries

DROP VIEW orders_summaries;

CREATE VIEW orders_summaries AS
SELECT 
order_id, 
user_id,
full_name,
email,
phone,
address,
SUM(total_price) AS total_price,
transaction_date
FROM orders_details 
GROUP BY order_id;

SELECT * FROM orders_summaries;
