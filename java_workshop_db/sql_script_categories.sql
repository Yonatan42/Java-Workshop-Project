USE java_workshop_db;

CREATE TABLE categories (
   id INT NOT NULL AUTO_INCREMENT,
   name VARCHAR (32) NOT NULL,
   created TIMESTAMP DEFAULT NOW(),
   modified TIMESTAMP DEFAULT NOW(),
   PRIMARY KEY (id)
);

CREATE TRIGGER trigger_categories_update
BEFORE UPDATE ON categories
FOR EACH ROW
SET NEW.modified = NOW();

# test category
# INSERT INTO categories (name) VALUES ('cat1');

#test update
# UPDATE categories  SET name = 'cat2' WHERE id = 1;

# delete all from table
# DELETE FROM categories WHERE id > 0;


# will have paging - the LIMIT can be used (if it gets 2 values then the first is the number to start AFTER and the second is the amount)