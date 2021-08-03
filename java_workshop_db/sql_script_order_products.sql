USE java_workshop_db;


DROP TRIGGER trigger_order_products_update;
DROP TABLE order_products;


CREATE TABLE order_products (
    order_id INT NOT NULL,
	product_id INT NOT NULL,
    price_at_order DECIMAL(10,2) NOT NULL,    
    quantity INT NOT NULL DEFAULT 1,
	created TIMESTAMP DEFAULT NOW(),
	modified TIMESTAMP DEFAULT NOW(),
    FOREIGN KEY (order_id) REFERENCES orders(id) ON UPDATE CASCADE ON DELETE RESTRICT,
    FOREIGN KEY (product_id) REFERENCES products(id) ON UPDATE CASCADE ON DELETE RESTRICT,
	UNIQUE (order_id, product_id)
);

 
CREATE TRIGGER trigger_order_products_update
BEFORE UPDATE ON order_products
FOR EACH ROW
SET NEW.modified = NOW();

# test category
INSERT INTO order_products (order_id, product_id, price_at_order, quantity) VALUES (2, 1, 15.25, 3);
INSERT INTO order_products (order_id, product_id, price_at_order) VALUES (2, 2, 4.25);


#test update
# UPDATE order_products  SET name = 'cat2' WHERE id = 1;

# delete all from table
DELETE FROM order_products WHERE id > 0;


# will have paging - the LIMIT can be used (if it gets 2 values then the first is the number to start AFTER and the second is the amount)


SELECT * FROM order_products;


