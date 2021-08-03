USE java_workshop_db;


DROP TRIGGER trigger_orders_update;
DROP TABLE orders;


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


