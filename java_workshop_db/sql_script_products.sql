USE java_workshop_db;

DROP TABLE products;

/*
CREATE TABLE products (
	id INT NOT NULL AUTO_INCREMENT,
	name VARCHAR (64) NOT NULL,
	description VARCHAR (256) NOT NULL,
    image_url VARCHAR(256),
	created TIMESTAMP DEFAULT NOW(),
	modified TIMESTAMP DEFAULT NOW(),
	PRIMARY KEY (id)
);

# changed descripton to TEXT and allowed NULL
ALTER TABLE products MODIFY description TEXT;

# renamed the name column to title
ALTER TABLE `products` CHANGE `name` `title` VARCHAR (64) NOT NULL;


# change name and type of image_url to image_data (make blob for base64 encoding and allow it to be null - will have a placeholder on the client)
ALTER TABLE `products` CHANGE `image_url` `image_data` BLOB;


ALTER TABLE `products` MODIFY `image_data` MEDIUMBLOB;
*/

CREATE TABLE products (
	id INT NOT NULL AUTO_INCREMENT,
	title VARCHAR (64) NOT NULL,
	description TEXT,
    image_url MEDIUMBLOB,
	created TIMESTAMP DEFAULT NOW(),
	modified TIMESTAMP DEFAULT NOW(),
	PRIMARY KEY (id)
);

CREATE TRIGGER trigger_products_update
BEFORE UPDATE ON products
FOR EACH ROW
SET NEW.modified = NOW();




# test category
# INSERT INTO products (title, description) VALUES ('prod1', 'desc1');
INSERT INTO products (title) VALUES ('super test');

#test update
# UPDATE products  SET title = 'prod2' WHERE id = 1;-- 

# delete all from table
DELETE FROM products WHERE id > 0;





-- stored functions


-- syntax for creating a function
/*

DELIMITER $$

CREATE FUNCTION function_name(
    param1,
    param2,…
)
RETURNS datatype
[NOT] DETERMINISTIC
BEGIN
 -- statements
END $$

DELIMITER ;

*/

-- store function example
/*

DELIMITER $$

CREATE FUNCTION CustomerLevel(
	credit DECIMAL(10,2)
) 
RETURNS VARCHAR(20)
DETERMINISTIC
BEGIN
    DECLARE customerLevel VARCHAR(20);

    IF credit > 50000 THEN
		SET customerLevel = 'PLATINUM';
    ELSEIF (credit >= 50000 AND 
			credit <= 10000) THEN
        SET customerLevel = 'GOLD';
    ELSEIF credit < 10000 THEN
        SET customerLevel = 'SILVER';
    END IF;
	-- return the customer level
	RETURN (customerLevel);
END$$
DELIMITER ;

*/

-- function for rand between two numbers
DELIMITER $$

CREATE FUNCTION rand_int(
	min_num INT,
    max_num INT
) 
RETURNS INT
NOT DETERMINISTIC
BEGIN
    RETURN ROUND((RAND() * (max_num-min_num))+min_num);
END$$
DELIMITER ;

# SELECT rand_int(10,20);


-- sotred prodecures

DROP PROCEDURE insert_product_random;

DELIMITER $$
CREATE PROCEDURE insert_product_random(IN amount INT)
BEGIN
	DECLARE x  INT;
    DECLARE rand_num  INT;
    DECLARE max_num  INT;
    DECLARE min_num  INT;
        
	SET x = 1;
    		
	SET max_num = 999999;
	SET min_num = 100000;
        
	loop_label:  LOOP
		IF  x > amount THEN 
			LEAVE  loop_label;
		END  IF;

        SET rand_num = rand_int(max_num, min_num);
        INSERT INTO products (name, description) VALUES (CONCAT('prod', rand_num), CONCAT('desc', rand_num));
		
        SET  x = x + 1;
	END LOOP;
END$$

DELIMITER ;



CALL insert_product_random(10);






DROP PROCEDURE insert_some_products;

DELIMITER $$
CREATE PROCEDURE insert_some_products(IN amount INT)
BEGIN
	DECLARE x  INT;
    DECLARE next_num  INT;
        
	SET x = 1;
        
	loop_label:  LOOP
		IF  x > amount THEN 
			LEAVE  loop_label;
		END  IF;

        SET next_num = get_next_auto_increment('products');

        INSERT INTO products (name, description) VALUES (CONCAT('prod', next_num), CONCAT('desc', next_num));
		
        SET  x = x + 1;
	END LOOP;
END$$

DELIMITER ;



CALL insert_some_products(10);






DROP VIEW products_with_categories;

CREATE VIEW products_with_categories AS
SELECT 
pc.product_id, 
p.title, 
p.description, 
p.image_data, 
pc.category_id, 
c.title AS category 
FROM products AS p 
INNER JOIN products_categories AS pc ON p.id = pc.product_id 
INNER JOIN categories AS c ON pc.category_id = c.id;





DROP VIEW products_catalog;

CREATE VIEW products_catalog AS
SELECT 
p.*,
s.id AS stock_id,
s.quantity,
s.price,
s.is_enabled
FROM products_with_categories AS p 
INNER JOIN stock AS s ON p.product_id = s.product_id;


SELECT * FROM products_catalog;

SELECT * FROM stock;

