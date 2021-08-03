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
*/

CREATE TABLE orders (
	id INT NOT NULL AUTO_INCREMENT,
	customer_id INT NOT NULL,
    phone VARCHAR(20) NOT NULL, # may be different from the one registered for the customer
    address TEXT NOT NULL, # may be different from the one registered for the customer
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


CREATE VIEW orders_details AS
SELECT 
o.id AS order_id, 
c.id AS customer_id,
p.id AS product_id,
CONCAT(c.last_name,  ' , ', c.first_name) AS customer_name,
c.email,
o.phone,
o.address,
p.title AS product_name,
op.price_at_order AS price,
op.quantity,
op.price_at_order * op.quantity AS total_price
FROM orders AS o 
INNER JOIN customers AS c ON o.customer_id = c.id 
INNER JOIN order_products AS op ON o.id = op.order_id
INNER JOIN products as p ON op.product_id = p.id;



SELECT * FROM orders_details;


-- orders summaries

CREATE VIEW orders_summaries AS
SELECT 
order_id, 
customer_id,
customer_name,
email,
phone,
address,
SUM(total_price) AS total_price 
FROM orders_details 
GROUP BY order_id;

SELECT * FROM orders_summaries;
