USE java_workshop_db;


DROP TRIGGER trigger_stock_update;
DROP TABLE stock;

/*
CREATE TABLE stock (
	id INT NOT NULL AUTO_INCREMENT,
	product_id INT NOT NULL,
    quantity INT NOT NULL DEFAULT 0,
    price DECIMAL(10,2) NOT NULL DEFAULT 0.0,
    is_enabled TINYINT(1) NOT NULL DEFAULT 0,
	created TIMESTAMP DEFAULT NOW(),
	modified TIMESTAMP DEFAULT NOW(),
    FOREIGN KEY (product_id) REFERENCES products(id) ON UPDATE CASCADE ON DELETE RESTRICT,
	PRIMARY KEY (id)
);


ALTER TABLE stock MODIFY product_id INT NOT NULL UNIQUE;
*/

CREATE TABLE stock (
	id INT NOT NULL AUTO_INCREMENT,
	product_id INT NOT NULL UNIQUE,
    quantity INT NOT NULL DEFAULT 0,
    price DECIMAL(10,2) NOT NULL DEFAULT 0.0,
    is_enabled TINYINT(1) NOT NULL DEFAULT 0,
	created TIMESTAMP DEFAULT NOW(),
	modified TIMESTAMP DEFAULT NOW(),
    FOREIGN KEY (product_id) REFERENCES products(id) ON UPDATE CASCADE ON DELETE RESTRICT,
	PRIMARY KEY (id)
);


 # stock are inactive by default and need to be manually activated
 
CREATE TRIGGER trigger_stock_update
BEFORE UPDATE ON stock
FOR EACH ROW
SET NEW.modified = NOW();

# test category
INSERT INTO stock (product_id) VALUES (1);

#test update
# UPDATE stock  SET name = 'cat2' WHERE id = 1;

# delete all from table
# DELETE FROM stock WHERE id > 0;


# will have paging - the LIMIT can be used (if it gets 2 values then the first is the number to start AFTER and the second is the amount)


SELECT * FROM stock;