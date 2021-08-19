TYPE=VIEW
query=select `pc`.`product_id` AS `product_id`,`p`.`title` AS `title`,`p`.`description` AS `description`,`p`.`image_data` AS `image_data`,`pc`.`category_id` AS `category_id`,`c`.`title` AS `category` from ((`java_workshop_db`.`products` `p` join `java_workshop_db`.`products_categories` `pc` on((`p`.`id` = `pc`.`product_id`))) join `java_workshop_db`.`categories` `c` on((`pc`.`category_id` = `c`.`id`)))
md5=91aa3c572e6850db1a83294b1915b301
updatable=1
algorithm=0
definer_user=admin
definer_host=localhost
suid=2
with_check_option=0
timestamp=2021-08-19 17:20:17
create-version=1
source=SELECT \npc.product_id, \np.title, \np.description, \np.image_data, \npc.category_id, \nc.title AS category \nFROM products AS p \nINNER JOIN products_categories AS pc ON p.id = pc.product_id \nINNER JOIN categories AS c ON pc.category_id = c.id
client_cs_name=utf8
connection_cl_name=utf8_general_ci
view_body_utf8=select `pc`.`product_id` AS `product_id`,`p`.`title` AS `title`,`p`.`description` AS `description`,`p`.`image_data` AS `image_data`,`pc`.`category_id` AS `category_id`,`c`.`title` AS `category` from ((`java_workshop_db`.`products` `p` join `java_workshop_db`.`products_categories` `pc` on((`p`.`id` = `pc`.`product_id`))) join `java_workshop_db`.`categories` `c` on((`pc`.`category_id` = `c`.`id`)))
