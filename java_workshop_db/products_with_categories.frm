TYPE=VIEW
query=select `pc`.`product_id` AS `product_id`,`p`.`name` AS `name`,`p`.`description` AS `description`,`p`.`image_url` AS `image_url`,`pc`.`category_id` AS `category_id`,`c`.`name` AS `category` from ((`java_workshop_db`.`products` `p` join `java_workshop_db`.`products_categories` `pc` on((`p`.`id` = `pc`.`product_id`))) join `java_workshop_db`.`categories` `c` on((`pc`.`category_id` = `c`.`id`)))
md5=7263570f929cf5acaa0c07b442b18130
updatable=1
algorithm=0
definer_user=admin
definer_host=localhost
suid=2
with_check_option=0
timestamp=2021-07-30 13:43:31
create-version=1
source=SELECT pc.product_id, p.name, p.description, p.image_url, pc.category_id, c.name as category \nFROM products AS p INNER JOIN products_categories AS pc ON p.id = pc.product_id INNER JOIN categories AS c ON pc.category_id = c.id
client_cs_name=utf8
connection_cl_name=utf8_general_ci
view_body_utf8=select `pc`.`product_id` AS `product_id`,`p`.`name` AS `name`,`p`.`description` AS `description`,`p`.`image_url` AS `image_url`,`pc`.`category_id` AS `category_id`,`c`.`name` AS `category` from ((`java_workshop_db`.`products` `p` join `java_workshop_db`.`products_categories` `pc` on((`p`.`id` = `pc`.`product_id`))) join `java_workshop_db`.`categories` `c` on((`pc`.`category_id` = `c`.`id`)))
