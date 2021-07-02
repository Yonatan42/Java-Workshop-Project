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
# INSERT INTO products_categories (product_id, category_id) VALUES (2, 2);

#test update
# UPDATE products_categories  SET category_id = 4 WHERE product_id = 2;-- 

# delete all from table
DELETE FROM products_categories WHERE product_id > 0;

-- will need to make a procedure and function that inserts into this table when creating a new product