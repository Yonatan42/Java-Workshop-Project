USE java_workshop_db;


# merging admins and customers into users


DROP VIEW users;

DROP TABLE admins;

ALTER TABLE customers  RENAME TO users;

DROP TRIGGER trigger_customers_update;
DROP TRIGGER trigger_customers_insert;

CREATE TRIGGER trigger_users_update
BEFORE UPDATE ON users
FOR EACH ROW
SET NEW.modified = NOW();

CREATE TRIGGER trigger_users_insert
BEFORE INSERT ON users 
FOR EACH ROW
SET NEW.secret_key = generate_secret_key();

ALTER TABLE users ADD is_admin TINYINT(1) NOT NULL DEFAULT 0 AFTER address;


#




DROP PROCEDURE refresh_secret_key;

DELIMITER $$
CREATE PROCEDURE refresh_secret_key(
	IN p_email VARCHAR(320)
)
BEGIN
    
		UPDATE users SET secret_key = generate_secret_key() WHERE email = p_email;
    
END$$

DELIMITER ;


SELECT * FROM users WHERE email = 'y@y.y';
CALL refresh_secret_key('y@y.y');
SELECT * FROM users WHERE email = 'y@y.y';