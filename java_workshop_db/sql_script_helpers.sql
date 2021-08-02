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







/*
DROP FUNCTION get_random_id;


DELIMITER $$

CREATE FUNCTION get_random_id(
	my_table_name VARCHAR(32)
) 
RETURNS INT
NOT DETERMINISTIC
BEGIN
    RETURN (CALL get_random_id_proc(my_table_name) AS rand_id);
END$$
DELIMITER ;

SELECT get_random_id('products');
*/







DROP PROCEDURE get_random_id;


DELIMITER $$

CREATE PROCEDURE get_random_id(
	IN my_table_name VARCHAR(32),
    OUT uid BIGINT
) 
BEGIN
	SET uid = 0;
	SET @s = CONCAT('SELECT `id` INTO @uid FROM `', my_table_name, '` ORDER BY RAND() LIMIT 1');
    PREPARE QUERY FROM @s;
    EXECUTE QUERY;
    DEALLOCATE PREPARE QUERY;
	SET uid = @uid;
END$$
DELIMITER ;

-- CALL get_random_id('products');






DROP FUNCTION get_split_string_at_index;


DELIMITER $$

CREATE FUNCTION get_split_string_at_index(
	str VARCHAR(64),
    delim VARCHAR(32),
    idx INT # index is 1 based
) 
RETURNS VARCHAR(64)
DETERMINISTIC
BEGIN
    RETURN SUBSTRING_INDEX(SUBSTRING_INDEX(str, delim, idx), delim, -1);
END$$
DELIMITER ;

SELECT get_split_string_at_index('1,2,3,4', ',', 2);


