USE java_workshop_db;


DROP TRIGGER trigger_customers_update;
DROP TABLE customers;

/*
CREATE TABLE customers (
	id INT NOT NULL AUTO_INCREMENT,
	email VARCHAR(320) NOT NULL UNIQUE,
    pass VARBINARY(256) NOT NULL,
    phone VARCHAR(20),
    address TEXT,
	created TIMESTAMP DEFAULT NOW(),
	modified TIMESTAMP DEFAULT NOW(),
	PRIMARY KEY (id)
);


# added first and last name
ALTER TABLE customers ADD first_name VARCHAR(32) NOT NULL;
ALTER TABLE customers ADD last_name VARCHAR(32) NOT NULL;

# moved the new columns to the place I wanted
ALTER TABLE customers MODIFY first_name VARCHAR(32) NOT NULL AFTER pass;
ALTER TABLE customers MODIFY last_name VARCHAR(32) NOT NULL AFTER first_name;

# made pass work with recommendations for bcrypt
ALTER TABLE customers MODIFY pass CHAR(60) NOT NULL;

#
add secret key
ALTER TABLE customers ADD secret_key CHAR(64) NOT NULL AFTER address;

*/





CREATE TABLE customers (
	id INT NOT NULL AUTO_INCREMENT,
	email VARCHAR(320) NOT NULL UNIQUE,
    pass CHAR(60) NOT NULL,
    first_name VARCHAR(32) NOT NULL,
    last_name VARCHAR(32) NOT NULL,
    phone VARCHAR(20),
    address TEXT,
    secret_key CHAR(64) NOT NULL,
	created TIMESTAMP DEFAULT NOW(),
	modified TIMESTAMP DEFAULT NOW(),
	PRIMARY KEY (id)
);
 
CREATE TRIGGER trigger_customers_update
BEFORE UPDATE ON customers
FOR EACH ROW
SET NEW.modified = NOW();

CREATE TRIGGER trigger_customers_insert
BEFORE INSERT ON customers 
FOR EACH ROW
SET NEW.secret_key = generate_secret_key();

# test category
INSERT INTO customers (email, pass, first_name, last_name) VALUES ('sqwe@s.s', 'mysuperpass', 'Steve', 'Anderson');

#test update
# UPDATE customers  SET name = 'cat2' WHERE id = 1;

# delete all from table
DELETE FROM customers WHERE id > 0;


# will have paging - the LIMIT can be used (if it gets 2 values then the first is the number to start AFTER and the second is the amount)


SELECT * FROM customers;



/*
-- THIS ENTIRE SECTION WORKD, BUT I DECIDED TO MOVE IT TO THE SERVER --


-- encrypt password

DROP FUNCTION encrypt_password;


DELIMITER $$

CREATE FUNCTION encrypt_password(
	pass VARCHAR(32), 
	row_id INT)
RETURNS VARBINARY(256)
DETERMINISTIC
BEGIN
	DECLARE secret_key  CHAR(32);
	DECLARE sha2_size  INT;
    
	SET secret_key = '8pWIeLKIAXCPP2CHMtAMWUjWzf1GgeBx';
    SET sha2_size = 512;
    
    RETURN  AES_ENCRYPT(pass, SHA2(CONCAT(SHA2(row_id, sha2_size), secret_key), sha2_size));
END$$
DELIMITER ;

SELECT encrypt_password('myawesomepass', 3);







DROP PROCEDURE insert_customer;

DELIMITER $$
CREATE PROCEDURE insert_customer(
	IN p_email VARCHAR(320),
    IN p_pass VARBINARY(256),
    IN p_phone VARCHAR(20),
    IN p_address TEXT
)
BEGIN
    
    INSERT INTO customers(email, pass, phone, address) VALUES (p_email, encrypt_password(p_pass, get_next_auto_increment('customers')), p_phone, p_address);
    
END$$

DELIMITER ;



CALL insert_customer('y@y.y', 'mysuperpass', NULL, NULL);





-- EXCEPT THIS PART, IT DOESN'T WROK YET

DROP PROCEDURE attempt_login;

DELIMITER $$
CREATE PROCEDURE attempt_login(
	IN p_email VARCHAR(320),
	IN p_pass VARCHAR(32)
)
BEGIN
    DECLARE found_pass  VARBINARY(256);
    DECLARE found_id INT;
	
    SELECT id, pass INTO found_id, found_pass FROM customers WHERE email = p_email LIMIT 1;  #search unified view of customers and admins

SELECT found_id, found_pass;

#    IF found_pass = encrypt_password(p_pass, found_id) THEN
#		SELECT 1; # return token perhaps
#	ELSE
#		SELECT 0;
#    END IF;
    
    
END$$

DELIMITER ;



CALL attempt_login('y@y.y', 'mysuperpass');


*/


