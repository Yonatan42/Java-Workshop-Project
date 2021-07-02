USE java_workshop_db;

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

DROP FUNCTION rand_int;


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

SELECT rand_int(10,20);




DROP FUNCTION get_next_auto_increment;


DELIMITER $$

CREATE FUNCTION get_next_auto_increment(
	my_table_name VARCHAR(32)
) 
RETURNS INT
NOT DETERMINISTIC
BEGIN
    RETURN (SELECT AUTO_INCREMENT FROM information_schema.TABLES WHERE TABLE_NAME = my_table_name);
END$$
DELIMITER ;

SELECT get_next_auto_increment('products');




