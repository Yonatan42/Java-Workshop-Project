USE java_workshop_db;

DROP TABLE products_categories;

CREATE TABLE products_categories (
	product_id INT NOT NULL,
    category_id INT NOT NULL,
	created TIMESTAMP default now(),
	modified TIMESTAMP default now(),
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