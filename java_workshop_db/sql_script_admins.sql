USE java_workshop_db;


DROP TRIGGER trigger_admins_update;
DROP TABLE admins;

/*
CREATE TABLE admins (
	id INT NOT NULL AUTO_INCREMENT,
	email VARCHAR(320) NOT NULL UNIQUE,
    pass VARBINARY(256) NOT NULL,
    first_name VARCHAR(32) NOT NULL,
    last_name VARCHAR(32) NOT NULL,
    phone VARCHAR(20),
    address TEXT,
	created TIMESTAMP DEFAULT NOW(),
	modified TIMESTAMP DEFAULT NOW(),
	PRIMARY KEY (id)
);

# made pass work with recommendations for bcrypt
ALTER TABLE admins MODIFY pass CHAR(60) NOT NULL;

# add secret key
ALTER TABLE admins ADD secret_key CHAR(64) NOT NULL AFTER address;

*/



CREATE TABLE admins (
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
 
CREATE TRIGGER trigger_admins_update
BEFORE UPDATE ON admins
FOR EACH ROW
SET NEW.modified = NOW();


CREATE TRIGGER trigger_admins_insert
BEFORE INSERT ON admins 
FOR EACH ROW
SET NEW.secret_key = generate_secret_key();

# test category
INSERT INTO admins (email, pass, first_name, last_name) VALUES ('admin3@admin.admin', 'mysuperpass', 'admin2', 'administrator2');

#test update
# UPDATE customers  SET name = 'cat2' WHERE id = 1;

# delete all from table
DELETE FROM admins WHERE id > 0;


# will have paging - the LIMIT can be used (if it gets 2 values then the first is the number to start AFTER and the second is the amount)


SELECT * FROM admins;


# view that consolidates customers and admins together as users (used for login)

DROP VIEW users;

CREATE VIEW users AS
SELECT *, 0 AS is_admin FROM customers
UNION
SELECT *, 1 AS is_admin FROM admins;


SELECT * FROM users;







DROP FUNCTION generate_secret_key;

DELIMITER $$

CREATE FUNCTION generate_secret_key() 
RETURNS CHAR(64)
NOT DETERMINISTIC
BEGIN
    RETURN  SHA2(UUID(), 256);
END$$
DELIMITER ;


SELECT generate_secret_key();










DROP PROCEDURE refresh_secret_key;

DELIMITER $$
CREATE PROCEDURE refresh_secret_key(
	IN p_email VARCHAR(320)
)
BEGIN
    DECLARE p_id INT;
    DECLARE p_is_admin TINYINT(1);
    
    SELECT id, is_admin INTO p_id, p_is_admin FROM users WHERE email = p_email;
    
    IF p_is_admin THEN
		UPDATE admins SET secret_key = generate_secret_key() WHERE id = p_id;
	ELSE
		UPDATE customers SET secret_key = generate_secret_key() WHERE id = p_id;
	END IF;

    
END$$

DELIMITER ;



CALL refresh_secret_key('y@y.y');





