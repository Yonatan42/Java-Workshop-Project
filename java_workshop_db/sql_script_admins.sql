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

*/



CREATE TABLE admins (
	id INT NOT NULL AUTO_INCREMENT,
	email VARCHAR(320) NOT NULL UNIQUE,
    pass CHAR(60) NOT NULL,
    first_name VARCHAR(32) NOT NULL,
    last_name VARCHAR(32) NOT NULL,
    phone VARCHAR(20),
    address TEXT,
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
INSERT INTO customers (email, pass, first_name, last_name) VALUES ('admin3@admin.admin', 'mysuperpass', 'admin2', 'administrator2');

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









