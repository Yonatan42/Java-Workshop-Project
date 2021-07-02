USE java_workshop_db;

CREATE TABLE products (
	id INT NOT NULL AUTO_INCREMENT,
	name VARCHAR (64) NOT NULL,
	description VARCHAR (256) NOT NULL,
    image_url VARCHAR(256),
	created TIMESTAMP default now(),
	modified TIMESTAMP default now(),
	PRIMARY KEY (id)
);

CREATE TRIGGER trigger_products_update
BEFORE UPDATE ON products
FOR EACH ROW
SET NEW.modified = NOW();

# test category
# INSERT INTO products (name, description) VALUES ('prod1', 'desc1');

#test update
# UPDATE products  SET name = 'prod2' WHERE id = 1;-- 

# delete all from table
# DELETE FROM products WHERE id > 0;