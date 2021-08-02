USE java_workshop_db;


DROP TRIGGER trigger_customers_update;
DROP TABLE customers;

CREATE TABLE customers (
	id INT NOT NULL AUTO_INCREMENT,
	email VARCHAR(320) NOT NULL UNIQUE,
    quantity INT NOT NULL DEFAULT 0,
    price DECIMAL(10,2) NOT NULL DEFAULT 0.0,
    is_enabled TINYINT(1) NOT NULL DEFAULT 0,
	created TIMESTAMP DEFAULT NOW(),
	modified TIMESTAMP DEFAULT NOW(),
    FOREIGN KEY (product_id) REFERENCES products(id) ON UPDATE CASCADE ON DELETE RESTRICT,
	PRIMARY KEY (id)
);

 
CREATE TRIGGER trigger_customers_update
BEFORE UPDATE ON stock
FOR EACH ROW
SET NEW.modified = NOW();

# test category
INSERT INTO customers (product_id) VALUES (1);

#test update
# UPDATE customers  SET name = 'cat2' WHERE id = 1;

# delete all from table
# DELETE FROM customers WHERE id > 0;


# will have paging - the LIMIT can be used (if it gets 2 values then the first is the number to start AFTER and the second is the amount)


SELECT * FROM customers;





-- encrypt password

DROP FUNCTION encrypt_password;


DELIMITER $$

CREATE FUNCTION encrypt_password(
	pass VARCHAR(32), 
	row_id INT)
RETURNS BLOB
DETERMINISTIC
BEGIN
	SET secret_ket = 'a234dsfAd535a564sd67Asdf56asdFasd7Fasd34Fasd2fa';
    SET sha2_size = 512;
    
    RETURN  AES_ENCRYPT(pass, SHA2(CONCAT(SHA2(row_id, sha2_size), secret_ket), sha2_size))
END$$
DELIMITER ;

SELECT encrypt_password('myawesomepass', 3);

