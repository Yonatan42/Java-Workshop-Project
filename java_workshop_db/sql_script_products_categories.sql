USE java_workshop_db;

DROP TABLE products_categories;

CREATE TABLE products_categories (
	product_id INT NOT NULL,
    category_id INT NOT NULL,
	created TIMESTAMP DEFAULT NOW(),
	modified TIMESTAMP DEFAULT NOW(),
    FOREIGN KEY (product_id) REFERENCES products(id) ON UPDATE CASCADE ON DELETE RESTRICT,
    FOREIGN KEY (category_id) REFERENCES categories(id) ON UPDATE CASCADE ON DELETE RESTRICT,
	UNIQUE (product_id, category_id)
);

CREATE TRIGGER trigger_products_categories_update
BEFORE UPDATE ON products_categories
FOR EACH ROW
SET NEW.modified = NOW();

# test category
INSERT INTO products_categories (product_id, category_id) VALUES (1, 3);

#test update
# UPDATE products_categories  SET category_id = 4 WHERE product_id = 2;-- 

# delete all from table
DELETE FROM products_categories WHERE product_id > 0;

-- will need to make a procedure and function that inserts into this table when creating a new product



CREATE VIEW products_with_categories AS
SELECT pc.product_id, p.name, p.description, p.image_url, pc.category_id, c.name as category 
FROM products AS p INNER JOIN products_categories AS pc ON p.id = pc.product_id INNER JOIN categories AS c ON pc.category_id = c.id;

SELECT * FROM products_with_categories WHERE product_id = 1;
SELECT * FROM products_with_categories WHERE category LIKE '%3%';








-- ------------------------ Insert Products with Random Categories ----------------------


DROP PROCEDURE insert_some_products_with_categories;

DELIMITER $$
CREATE PROCEDURE insert_some_products_with_categories(IN amount INT)
BEGIN
	DECLARE x  INT;
    DECLARE next_num  INT;
    DECLARE cat_id INT;
        
	SET x = 1;
	
	loop_label:  LOOP
		IF  x > amount THEN 
			LEAVE  loop_label;
		END  IF;

        SET next_num = get_next_auto_increment('products');

        INSERT INTO products (name, description) VALUES (CONCAT('prod', next_num), CONCAT('desc', next_num));
		CALL get_random_id('categories', cat_id);
		
        /*
        
        do more than one, do the random 3 times and check if the pair exists aready, if not, add to the bride table
        */
        
        
        SET  x = x + 1;
	END LOOP;
    
END$$

DELIMITER ;



CALL insert_some_products_with_categories(10);







-- ------------------------ Insert Products with  Categories ----------------------

-- needed for project!!


DROP PROCEDURE insert_products_with_categories;

DELIMITER $$
CREATE PROCEDURE insert_products_with_categories(
	IN p_name VARCHAR (64),
	IN p_description VARCHAR (256),
    IN p_image_url VARCHAR(256),
    IN p_categories_string VARCHAR (64) # a comma separated string of category id's
)
BEGIN
	DECLARE x  INT;    
    DECLARE current_cat_id INT;
    DECLARE end_symbol INT;
    DECLARE product_id INT;
    
    
	DECLARE errno INT;
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
	BEGIN
    ROLLBACK;
    DELETE FROM products WHERE id = product_id; # delete the inserted product if the transaction fails (meaning that there was an issue with one of the categories)
    RESIGNAL; # rethrow the exeption that was caught
    END;
    
    
    SET end_symbol =  -1;
    
    INSERT INTO products (name, description, image_url) VALUES (p_name, p_description, p_image_url);
    
    SET product_id = (SELECT LAST_INSERT_ID() AS id FROM products LIMIT 1);

    
    START TRANSACTION; # only start transaction once the new product is inserted so that the id can be used as a primary key
    
        
	SET x = 1;
	
	loop_label:  LOOP
		SET current_cat_id = (SELECT get_split_string_at_index(CONCAT(p_categories_string, ',' ,end_symbol), ',', x) LIMIT 1);
		IF  current_cat_id = end_symbol THEN 
			LEAVE  loop_label;
		END  IF;
        
		INSERT INTO products_categories (product_id, category_id) VALUES (product_id, current_cat_id);
        
        SET  x = x + 1;
	END LOOP;
    
    
	COMMIT WORK;
    
END$$

DELIMITER ;



CALL insert_products_with_categories('pc', 'pc test', NULL, '4,8,12');
-- CALL insert_products_with_categories('pc', 'pc test', NULL, '4,8,18'); # should fail and when it does no new product should exist