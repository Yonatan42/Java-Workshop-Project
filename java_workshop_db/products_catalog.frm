TYPE=VIEW
query=select `p`.`product_id` AS `product_id`,`p`.`title` AS `title`,`p`.`description` AS `description`,`p`.`image_data` AS `image_data`,`p`.`category_id` AS `category_id`,`p`.`category` AS `category`,`s`.`id` AS `stock_id`,`s`.`quantity` AS `quantity`,`s`.`price` AS `price`,`s`.`is_enabled` AS `is_enabled` from (`java_workshop_db`.`products_with_categories` `p` join `java_workshop_db`.`stock` `s` on((`p`.`product_id` = `s`.`product_id`)))
md5=21832afdd26750127fb25228ab1c807b
updatable=1
algorithm=0
definer_user=admin
definer_host=localhost
suid=2
with_check_option=0
timestamp=2021-08-19 17:27:22
create-version=1
source=SELECT \np.*,\ns.id AS stock_id,\ns.quantity,\ns.price,\ns.is_enabled\nFROM products_with_categories AS p \nINNER JOIN stock AS s ON p.product_id = s.product_id
client_cs_name=utf8
connection_cl_name=utf8_general_ci
view_body_utf8=select `p`.`product_id` AS `product_id`,`p`.`title` AS `title`,`p`.`description` AS `description`,`p`.`image_data` AS `image_data`,`p`.`category_id` AS `category_id`,`p`.`category` AS `category`,`s`.`id` AS `stock_id`,`s`.`quantity` AS `quantity`,`s`.`price` AS `price`,`s`.`is_enabled` AS `is_enabled` from (`java_workshop_db`.`products_with_categories` `p` join `java_workshop_db`.`stock` `s` on((`p`.`product_id` = `s`.`product_id`)))
